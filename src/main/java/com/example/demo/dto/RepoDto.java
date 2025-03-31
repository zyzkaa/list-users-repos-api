package com.example.demo.dto;

public class RepoDto {
    private String name;
    private boolean fork;
    private String branches_url;
    private BranchDto[] branches;
    private OwnerDto owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public String getBranches_url() {
        return branches_url;
    }

    public void setBranches_url(String branches_url) {
        this.branches_url = branches_url;
    }

    public BranchDto[] getBranches() {
        return branches;
    }

    public void setBranches(BranchDto[] branches) {
        this.branches = branches;
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public void setOwner(OwnerDto owner) {
        this.owner = owner;
    }
}
