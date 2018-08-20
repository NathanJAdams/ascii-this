package com.repocleaner.repohosts;

public class GitHubRepoHost extends RepoHost {
    public GitHubRepoHost() {
        super(
                "GitHub",
                "https://github.com",
                "/user/USER",
                "/USER/REPO",
                "https://api.github.com",
                "/user/USER/repos",
                "/repos/USER/REPO/git/refs",
                "/repos/USER/REPO/pulls"
        );
    }
}
