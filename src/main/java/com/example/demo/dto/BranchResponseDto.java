package com.example.demo.dto;

public class BranchResponseDto {
    private String name;
    private String lastCommitSha;

    public BranchResponseDto(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }

    public String getName() {
        return name;
    }

    public String getLastCommitSha() {
        return lastCommitSha;
    }
}
