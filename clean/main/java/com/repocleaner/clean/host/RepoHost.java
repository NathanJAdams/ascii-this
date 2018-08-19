package com.repocleaner.clean.host;

import org.apache.http.HttpStatus;
import com.repocleaner.clean.rest.Rest;
import com.repocleaner.clean.rest.RestRequest;
import com.repocleaner.clean.rest.RestResponse;
import com.repocleaner.clean.util.GitUtil;
import com.repocleaner.clean.util.RepoCleanerException;

public abstract class RepoHost {
    private final String name;
    private final String urlRoot;
    private final String urlUser;
    private final String urlRepo;
    private final String apiUrlRoot;
    private final String apiUrlRepos;
    private final String apiUrlBranches;
    private final String apiUrlRaisePullRequest;

    public RepoHost(String name, String urlRoot, String urlUser, String urlRepo, String apiUrlRoot, String apiUrlRepos, String apiUrlBranches, String apiUrlRaisePullRequest) {
        this.name = name;
        this.urlRoot = urlRoot;
        this.urlUser = urlUser;
        this.urlRepo = urlRepo;
        this.apiUrlRoot = apiUrlRoot;
        this.apiUrlRepos = apiUrlRepos;
        this.apiUrlBranches = apiUrlBranches;
        this.apiUrlRaisePullRequest = apiUrlRaisePullRequest;
    }

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

    public void raisePullRequest(String userName, String token, String repoName, String title, String description, String dirtyBranch, String cleanedBranch) throws RepoCleanerException {
        String url = createApiUrlRaisePullRequest(userName, repoName);
        RaisePullRequestBody raisePullRequestBody = new RaisePullRequestBody(title, description, cleanedBranch, dirtyBranch);
        RestRequest<String> request = RestRequest.POST(url, raisePullRequestBody, String.class)
                .withBasicAuth(GitUtil.REPO_CLEANER_AUTHOR.getName(), token);
        RestResponse<String> response = new Rest().getResponse(request);
        int status = response.getStatus();
        if ((status < HttpStatus.SC_OK) || (status >= HttpStatus.SC_MULTIPLE_CHOICES)) {
            throw new RepoCleanerException("Failed to raise pull request, [status=" + status + "],[message=" + response.getResponse() + ']');
        }
    }
}
