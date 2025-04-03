package com.example.demo.service;

import com.example.demo.dto.BranchDto;
import com.example.demo.dto.RepoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class GithubFetchSerivce {
    private final RestTemplate restTemplate;
    @Value("${github.api.url}")
    private String githubApiUrl;

    public GithubFetchSerivce(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RepoDto[] fetchRepos(String username) {
        String url = githubApiUrl + "/users/" + username + "/repos?type=all";

        var repos = Arrays.stream(restTemplate.getForObject(url, RepoDto[].class))
                .filter(repo -> !repo.isFork())
                .toArray(RepoDto[]::new);

        for (RepoDto repo : repos) {
            url = repo.getBranches_url().substring(0, repo.getBranches_url().length() - "/{branch}".length());
            repo.setBranches(restTemplate.getForObject(url, BranchDto[].class));
        }
        return repos;
    }
}
