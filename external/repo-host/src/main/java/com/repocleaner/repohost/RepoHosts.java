package com.repocleaner.repohost;

import com.repocleaner.util.RepoCleanerException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RepoHosts {
    public static final RepoHosts DEFAULT_INSTANCE = new RepoHosts();

    private static final String REPO_CLEANER_AUTHOR = "repocleaner.com";

    private final Map<String, RepoHostBase> namedRepoHosts = new HashMap<>();

    public RepoHosts() {
        add(new GitHubRepoHost());
        add(new BitBucketRepoHost());
    }

    public Collection<RepoHostBase> getAll() {
        return namedRepoHosts.values();
    }

    public RepoHostBase get(String hostname) throws RepoCleanerException {
        RepoHostBase repoHost = namedRepoHosts.get(hostname);
        if (repoHost == null) {
            throw new RepoCleanerException("Failed to find repo host: " + hostname);
        }
        return repoHost;
    }

    public void add(RepoHostBase repoHost) {
        namedRepoHosts.put(repoHost.getName(), repoHost);
    }
}
