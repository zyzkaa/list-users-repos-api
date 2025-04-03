package com.example.demo.controller;

import com.example.demo.dto.BranchDto;
import com.example.demo.dto.BranchResponseDto;
import com.example.demo.dto.RepoDto;
import com.example.demo.dto.RepoResponseDto;
import com.example.demo.service.GithubFetchSerivce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

@RestController
public class Controller {
    GithubFetchSerivce githubFetchSerivce;

    public Controller(GithubFetchSerivce githubFetchSerivce) {
        this.githubFetchSerivce = githubFetchSerivce;
    }

    @GetMapping("/{username}")
    public ResponseEntity<RepoResponseDto[]> getRepoList(@PathVariable String username) throws JsonProcessingException {
        RepoDto[] repos = githubFetchSerivce.fetchRepos(username);
        ArrayList<RepoResponseDto> responseRepos = new ArrayList<>();
        for(RepoDto repo : repos){
            ArrayList<BranchResponseDto> responseBranches = new ArrayList<BranchResponseDto>();
            for (BranchDto branch : repo.getBranches()) {
                responseBranches.add(new BranchResponseDto(branch.getName(), branch.getCommit().getSha()));
            }
            responseRepos.add(new RepoResponseDto(repo.getName(), repo.getOwner().getLogin(), responseBranches.toArray(new BranchResponseDto[0])));
        }
        return ResponseEntity.ok(responseRepos.toArray(new RepoResponseDto[0]));
    }

    //to avoid getting errors
    @GetMapping("/favicon.ico")
    public void ignoreFavicon(){

    }
}
