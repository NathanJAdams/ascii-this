package com.repocleaner.repohosts;

import com.repocleaner.util.rest.RestRequest;
import com.repocleaner.util.rest.RestResponse;
import com.repocleaner.util.rest.RestUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class RepoHost {
    private static final String REPO_CLEANER_AUTHOR = "repocleaner.com";

    private final String name;
    private final String urlRoot;
    private final String urlUser;
    private final String urlRepo;
    private final String apiUrlRoot;
    private final String apiUrlRepos;
    private final String apiUrlBranches;
    private final String apiUrlRaisePullRequest;

    public String getName() {
        return name;
    }

    public String createUrlUser(String user) {
        return urlRoot + urlUser.replace("USER", user);
    }

    public String createUrlRepo(String user, String repo) {
        return urlRoot + urlRepo.replace("USER", user).replace("REPO", repo);
    }

    public String createApiUrlRepos(String user) {
        return apiUrlRoot + apiUrlRepos.replace("USER", user);
    }

    public String createApiUrlBranches(String user, String repo) {
        return apiUrlRoot + apiUrlBranches.replace("USER", user).replace("REPO", repo);
    }

    public String createApiUrlFork(String user, String repo) {
        return apiUrlRoot + apiUrlRaisePullRequest.replace("USER", user).replace("REPO", repo);
    }

    public String createApiUrlRaisePullRequest(String user, String repo) {
        return apiUrlRoot + apiUrlRaisePullRequest.replace("USER", user).replace("REPO", repo);
    }

    public RestResponse<String> raisePullRequest(String userName, String token, String repoName, String title, String description, String dirtyBranch, String cleanedBranch) {
        String url = createApiUrlRaisePullRequest(userName, repoName);
        RaisePullRequestBody raisePullRequestBody = new RaisePullRequestBody(title, description, cleanedBranch, dirtyBranch);
        RestRequest<String> request = RestRequest.POST(url, raisePullRequestBody, String.class)
                .withBasicAuth(REPO_CLEANER_AUTHOR, token);
        return RestUtil.getResponse(request);
    }
}
