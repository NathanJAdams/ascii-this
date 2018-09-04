package com.repocleaner.repohost;

import com.repocleaner.model.send.RaisePullRequestBody;
import com.repocleaner.model.repohost.RepoHost;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class RepoHostBase implements RepoHost {
    private final String name;
    private final String urlRoot;
    private final String urlUser;
    private final String urlRepo;
    private final String apiUrlRoot;
    private final String apiUrlRepos;
    private final String apiUrlBranches;
    private final String apiUrlRaisePullRequest;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String createUrlUser(String user) {
        return urlRoot + urlUser.replace("USER", user);
    }

    @Override
    public String createUrlRepo(String user, String repo) {
        return urlRoot + urlRepo.replace("USER", user).replace("REPO", repo);
    }

    @Override
    public String createApiUrlRepos(String user) {
        return apiUrlRoot + apiUrlRepos.replace("USER", user);
    }

    @Override
    public String createApiUrlBranches(String user, String repo) {
        return apiUrlRoot + apiUrlBranches.replace("USER", user).replace("REPO", repo);
    }

    @Override
    public String createApiUrlFork(String user, String repo) {
        return apiUrlRoot + apiUrlRaisePullRequest.replace("USER", user).replace("REPO", repo);
    }

    @Override
    public String createApiUrlRaisePullRequest(String user, String repo) {
        return apiUrlRoot + apiUrlRaisePullRequest.replace("USER", user).replace("REPO", repo);
    }

    @Override
    public Object createPullRequestBody(String userName, String token, String repoName, String title, String description, String dirtyBranch, String cleanedBranch) {
        return new RaisePullRequestBody(title, description, cleanedBranch, dirtyBranch);
    }
}
