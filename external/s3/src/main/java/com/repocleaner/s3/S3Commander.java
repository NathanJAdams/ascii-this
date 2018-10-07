package com.repocleaner.s3;

import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public class S3Commander {
    public static boolean download(String bucket, String key, File file) throws RepoCleanerException {
        return new S3DownloadCommand(bucket, key, file).download();
    }

    public static boolean upload(String bucket, String key, File file) throws RepoCleanerException {
        return new S3UploadCommand(bucket, key, file).upload();
    }

    public static boolean delete(String bucket, String key) throws RepoCleanerException {
        return new S3DeleteCommand(bucket, key).delete();
    }
}
