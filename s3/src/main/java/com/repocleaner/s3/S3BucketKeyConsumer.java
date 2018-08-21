package com.repocleaner.s3;

import com.repocleaner.util.RepoCleanerException;

@FunctionalInterface
public interface S3BucketKeyConsumer {
    void consume(String bucket, String key) throws RepoCleanerException;
}
