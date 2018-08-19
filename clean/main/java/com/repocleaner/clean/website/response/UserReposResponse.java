package com.repocleaner.clean.website.response;

import java.util.List;

public class UserReposResponse {
    private final boolean isValid;
    private final List<UserRepoResponse> userRepos;

    public UserReposResponse(boolean isValid, List<UserRepoResponse> userRepos) {
        this.isValid = isValid;
        this.userRepos = userRepos;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<UserRepoResponse> getUserRepos() {
        return userRepos;
    }
}
