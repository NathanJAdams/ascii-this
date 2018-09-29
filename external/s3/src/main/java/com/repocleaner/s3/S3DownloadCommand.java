package com.repocleaner.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;

@AllArgsConstructor
public class S3DownloadCommand {
    private final String bucket;
    private final String key;
    private final File file;

    public boolean download() throws RepoCleanerException {
        try {
            S3Object object = S3Info.CLIENT.getObject(bucket, key);
            S3ObjectInputStream inputStream = object.getObjectContent();
            IOUtils.copy(inputStream, file);
            return true;
        } catch (SdkClientException | IOException e) {
            throw new RepoCleanerException("Failed to download file from S3", e);
        }
    }
}
