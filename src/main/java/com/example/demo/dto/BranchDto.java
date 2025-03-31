package com.example.demo.dto;

public class BranchDto {
    private String name;
    private CommitDto commit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommitDto getCommit() {
        return commit;
    }

    public void setCommit(CommitDto commit) {
        this.commit = commit;
    }
}
