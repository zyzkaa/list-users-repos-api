package com.example.demo;

import com.example.demo.service.GithubFetchSerivce;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GithubFetchServiceIT {
    private final GithubFetchSerivce githubFetchSerivce;

    public GithubFetchServiceIT(GithubFetchSerivce githubFetchSerivce) {
        this.githubFetchSerivce = githubFetchSerivce;
    }
}
