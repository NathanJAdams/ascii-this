package com.repocleaner.repohost;

public class BitBucketRepoHost extends RepoHostBase {
    public BitBucketRepoHost() {
        super(
                "BitBucket",
                "https://bitbucket.org",
                "/USER",
                "/USER/REPO",
                "https://api.bitbucket.org",
                "/2.0/repositories/USER",
                "/2.0/repositories/USER/REPO/refs/branches",
                "/rest/api/1.0/projects/PROJECT/repos/REPO/pull-requests"
        );
    }
}
