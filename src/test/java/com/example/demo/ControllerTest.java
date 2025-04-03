package com.example.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WireMockTest(httpPort = 8089)
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static WireMockServer wireMockServer;

    @BeforeEach
    public void setupWireMock() {
        WireMock.reset();
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlPathEqualTo("/users/testuser/repos"))
                .willReturn(okJson("""
                    [
                      {
                        "name": "real-repo",
                        "fork": false,
                        "owner": { "login": "testuser" },
                        "branches_url": "http://localhost:8089/repos/testuser/real-repo/branches/{branch}"
                      },
                      {
                        "name": "forked-repo",
                        "fork": true,
                        "owner": { "login": "testuser" },
                        "branches_url": "http://localhost:8089/repos/testuser/forked-repo/branches/{branch}"
                      }
                    ]
                """)));

        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlPathEqualTo("/repos/testuser/real-repo/branches"))
                .willReturn(okJson("""
                    [
                      {
                        "name": "main",
                        "commit": {
                          "sha": "abc123"
                        }
                      },
                      {
                        "name": "dev",
                        "commit": {
                          "sha": "def456"
                        }
                      }
                    ]
                """)));

        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlPathEqualTo("/users/doesNotExist/repos"))
                .willReturn(aResponse()
                        .withStatus(404)));

        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlPathEqualTo("/users/noBranchesUser/repos"))
                .willReturn(okJson("""
                [
                  {
                    "name": "no-branches-repo",
                    "fork": false,
                    "owner": { "login": "noBranchesUser" },
                    "branches_url": "http://localhost:8089/repos/noBranchesUser/no-branches-repo/branches/{branch}"
                  }
                ]
            """)));

        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo("/repos/noBranchesUser/no-branches-repo/branches"))
                .willReturn(okJson("[]")));

        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlPathEqualTo("/users/noReposUser/repos"))
                .willReturn(okJson("[]")));
    }

    @DynamicPropertySource
    static void setUrl(DynamicPropertyRegistry registry) {
        registry.add("github.api.url", () -> "http://localhost:8089");
    }

    @Test
    public void testDoesNotReturnForks() throws Exception {
        mockMvc.perform(get("/testuser").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].repositoryName").value("real-repo"))
                .andExpect(jsonPath("$[0].ownerLogin").value("testuser"))
                .andExpect(jsonPath("$[0].branches.length()").value(2))
                .andExpect(jsonPath("$[0].branches[0].name").value("main"))
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").value("abc123"))
                .andExpect(jsonPath("$[0].branches[1].name").value("dev"))
                .andExpect(jsonPath("$[0].branches[1].lastCommitSha").value("def456"));
    }

    @Test
    public void testUserNotFound() throws Exception {
        mockMvc.perform(get("/doesNotExist").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testNoBranches() throws Exception {
        mockMvc.perform(get("/noBranchesUser").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].repositoryName").value("no-branches-repo"))
                .andExpect(jsonPath("$[0].ownerLogin").value("noBranchesUser"))
                .andExpect(jsonPath("$[0].branches.length()").value(0));
    }

    @Test
    public void testNoRepos() throws Exception {
        mockMvc.perform(get("/noReposUser").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

}