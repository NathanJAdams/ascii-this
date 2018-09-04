package com.repocleaner.model.repohost;

public interface RepoHost {
    String REPO_CLEANER_AUTHOR = "repocleaner.com";

    String getName();

    String createUrlUser(String user);

    String createUrlRepo(String user, String repo);

    String createApiUrlRepos(String user);

    String createApiUrlBranches(String user, String repo);

    String createApiUrlFork(String user, String repo);

    String createApiUrlRaisePullRequest(String user, String repo);

    Object createPullRequestBody(String userName, String token, String repoName, String title, String description, String dirtyBranch, String cleanedBranch);
}
