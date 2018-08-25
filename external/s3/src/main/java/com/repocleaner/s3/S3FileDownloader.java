package com.repocleaner.s3;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;
import java.io.IOException;

public class S3FileDownloader {
    public static void download(String bucket, String key, File file) throws RepoCleanerException {
        S3Object object = S3Info.CLIENT.getObject(bucket, key);
        S3ObjectInputStream inputStream = object.getObjectContent();
        try {
            IOUtils.copy(inputStream, file);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to copy cleaned repo to sent file", e);
        }
    }
}
