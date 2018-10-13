package com.repocleaner.sink;

import com.repocleaner.model.RepoHost;
import com.repocleaner.model.Sink;
import com.repocleaner.repohost.RepoHostBase;
import com.repocleaner.repohost.RepoHosts;
import com.repocleaner.corerepotoken.RepoToken;
import com.repocleaner.util.CleanResult;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.rest.RestRequest;
import com.repocleaner.util.rest.RestUtil;
import lombok.AllArgsConstructor;
import org.eclipse.jgit.api.Git;

import java.io.File;

@AllArgsConstructor
public class RepoHostSink implements Sink {
    private static final RepoToken REPO_TOKEN = new RepoToken();

    private final String host;
    private final String user;
    private final String repo;
    private final String masterBranch;
    private final String encryptedToken;

    @Override
    public void upload(File sourceFolder, CleanResult cleanResult, File tempFile) throws RepoCleanerException {
        RepoHostBase repoHost = RepoHosts.DEFAULT_INSTANCE.get(host);
        String title = "Cleaned by repocleaner.com";
        String description = cleanResult.getDescription();
        String cleanedBranch = getBranchName(sourceFolder);
        String url = repoHost.createApiUrlRaisePullRequest(user, repo);
        String token = REPO_TOKEN.decryptToken(encryptedToken);
        Object pullRequestBody = repoHost.createPullRequestBody(user, token, repo, title, description, cleanedBranch, masterBranch);
        RestRequest<String> request = RestRequest.POST(url, pullRequestBody, String.class)
                .withBasicAuth(RepoHost.REPO_CLEANER_AUTHOR, token);
        RestUtil.getResponse(request);
    }

    private String getBranchName(File sourceFolder) throws RepoCleanerException {
        try (Git git = GitUtil.open(sourceFolder)) {
            return GitUtil.getBranchName(git);
        }
    }
}
