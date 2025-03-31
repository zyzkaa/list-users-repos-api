package com.example.demo.service;

import com.example.demo.dto.BranchDto;
import com.example.demo.dto.RepoDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GithubFetchSerivce {
    private final RestTemplate restTemplate;

    public GithubFetchSerivce(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RepoDto[] fetchRepos(String username) {
        String url = "https://api.github.com/users/" + username + "/repos?type=all";

//        try{
            RepoDto[] repos = restTemplate.getForObject(url, RepoDto[].class);
            for (RepoDto repo : repos) {
                url = repo.getBranches_url().substring(0, repo.getBranches_url().length() - "/{branch}".length());
                repo.setBranches(restTemplate.getForObject(url, BranchDto[].class));
            }
            return repos;
//        } catch (HttpClientErrorException e){
//            throw e;
//        }
    }
}
