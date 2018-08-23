package com.repocleaner.source;

import com.repocleaner.repohosts.RepoHost;
import com.repocleaner.repohosts.RepoHosts;
import com.repocleaner.util.CleanResult;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;
import org.eclipse.jgit.api.Git;

import java.io.File;

@AllArgsConstructor
public class RepoHostSource implements Source {
    private final String host;
    private final String user;
    private final String repo;
    private final String masterBranch;
    private final String token;

    public void saveSource(File sourceFolder) throws RepoCleanerException {
        RepoHost repoHost = RepoHosts.DEFAULT_INSTANCE.get(host);
        String uri = repoHost.createUrlRepo(user, repo);
        GitUtil.clone(uri, masterBranch, sourceFolder);
    }

    @Override
    public void sendCleaned(File sourceFolder, CleanResult cleanResult, File tempFile) throws RepoCleanerException {
        RepoHost repoHost = RepoHosts.DEFAULT_INSTANCE.get(host);
        String title = "Cleaned by repocleaner.com";
        String description = cleanResult.getDescription();
        String cleanedBranch = getBranchName(sourceFolder);
        repoHost.raisePullRequest(user, token, repo, title, description, masterBranch, cleanedBranch);
    }

    private String getBranchName(File sourceFolder) throws RepoCleanerException {
        Git git = GitUtil.init(sourceFolder);
        return GitUtil.getBranchName(git);
    }
}
