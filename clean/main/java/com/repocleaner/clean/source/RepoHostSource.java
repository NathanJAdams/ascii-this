package com.repocleaner.clean.source;

import org.eclipse.jgit.api.Git;
import com.repocleaner.clean.host.RepoHost;
import com.repocleaner.clean.host.RepoHosts;
import com.repocleaner.clean.util.GitUtil;
import com.repocleaner.clean.util.RepoCleanerException;

import java.io.File;
import java.util.Set;

public class RepoHostSource implements Source {
    private static final RepoHosts REPO_HOSTS = new RepoHosts();

    private final String host;
    private final String user;
    private final String repo;
    private final String branch;
    private final String token;

    public RepoHostSource(String host, String user, String repo, String branch, String token) {
        this.host = host;
        this.user = user;
        this.repo = repo;
        this.branch = branch;
        this.token = token;
    }

    public Git saveCodeTo(File folder) throws RepoCleanerException {
        RepoHost repoHost = REPO_HOSTS.get(host);
        String uri = repoHost.createUrlRepo(user, repo);
        return GitUtil.clone(uri, branch, folder);
    }

    @Override
    public String createCleanBranchName(Git git) throws RepoCleanerException {
        Set<String> existingBranchNames = GitUtil.getBranchNames(git);

        StringBuilder sb = new StringBuilder();
        sb.append("repo-cleaner-");
        sb.append(branch);

        String branchTo = sb.toString();
        if (!existingBranchNames.contains(branchTo)) {
            return branchTo;
        }

        sb.append('_');
        int length = sb.length();

        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            sb.append(i);
            branchTo = sb.toString();
            if (!existingBranchNames.contains(branchTo)) {
                return branchTo;
            }
            sb.setLength(length);
        }
        throw new RepoCleanerException("Failed to create valid branch name");
    }

    @Override
    public void sendCleanedCode(Git git, String cleanedBranch, String title, String description) throws RepoCleanerException {
        RepoHost repoHost = REPO_HOSTS.get(host);
        String message = "This branch was cleaned by cleaner.com";
        GitUtil.commit(git, message);
        GitUtil.push(git, token);
        repoHost.raisePullRequest(user, token, repo, title, description, branch, cleanedBranch);
    }
}
