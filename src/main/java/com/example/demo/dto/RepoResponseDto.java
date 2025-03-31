package com.example.demo.dto;

public class RepoResponseDto {
    private String repositoryName;
    private String ownerLogin;
    private BranchResponseDto[] branches;

    public RepoResponseDto(String repositoryName, String ownerLogin, BranchResponseDto[] branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public BranchResponseDto[] getBranches() {
        return branches;
    }
}
