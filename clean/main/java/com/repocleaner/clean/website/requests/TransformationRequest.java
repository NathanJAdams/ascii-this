package com.repocleaner.clean.website.requests;

public class TransformationRequest {
    private final String repoHostName;
    private final String userName;
    private final String repoName;
    private final String branchName;
    private final String gitDiff;

    public TransformationRequest(String repoHostName, String userName, String repoName, String branchName, String gitDiff) {
        this.repoHostName = repoHostName;
        this.userName = userName;
        this.repoName = repoName;
        this.branchName = branchName;
        this.gitDiff = gitDiff;
    }

    public String getRepoHostName() {
        return repoHostName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getGitDiff() {
        return gitDiff;
    }
}
