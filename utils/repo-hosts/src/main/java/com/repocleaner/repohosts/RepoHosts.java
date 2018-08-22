package com.repocleaner.repohosts;

import com.repocleaner.util.RepoCleanerException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RepoHosts {
    public static final RepoHosts DEFAULT_INSTANCE = new RepoHosts();

    private final Map<String, RepoHost> namedRepoHosts = new HashMap<>();

    public RepoHosts() {
        add(new GitHubRepoHost());
        add(new BitBucketRepoHost());
    }

    public Collection<RepoHost> getAll() {
        return namedRepoHosts.values();
    }

    public RepoHost get(String hostname) throws RepoCleanerException {
        RepoHost repoHost = namedRepoHosts.get(hostname);
        if (repoHost == null) {
            throw new RepoCleanerException("Failed to find repo host: " + hostname);
        }
        return repoHost;
    }

    public void add(RepoHost repoHost) {
        namedRepoHosts.put(repoHost.getName(), repoHost);
    }
}
