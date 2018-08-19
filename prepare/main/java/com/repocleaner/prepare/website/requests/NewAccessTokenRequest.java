package com.repocleaner.clean.website.requests;

public class NewAccessTokenRequest {
    private final String repoHostName;
    private final String userName;
    private final String repoName;
    private final String token;

    public NewAccessTokenRequest(String repoHostName, String userName, String repoName, String token) {
        this.repoHostName = repoHostName;
        this.userName = userName;
        this.repoName = repoName;
        this.token = token;
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

    public String getToken() {
        return token;
    }
}
