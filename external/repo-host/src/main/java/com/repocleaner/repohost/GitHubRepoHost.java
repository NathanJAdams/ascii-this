package com.repocleaner.repohost;

public class GitHubRepoHost extends RepoHostBase {
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
