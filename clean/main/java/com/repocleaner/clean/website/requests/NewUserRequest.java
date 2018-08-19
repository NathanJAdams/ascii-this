package com.repocleaner.clean.website.requests;

public class NewUserRequest {
    private final String repoHost;
    private final String user;

    public NewUserRequest(String repoHost, String user) {
        this.repoHost = repoHost;
        this.user = user;
    }

    public String getRepoHost() {
        return repoHost;
    }

    public String getUser() {
        return user;
    }
}
