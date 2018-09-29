package com.repocleaner.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class S3UploadCommand {
    private final String bucket;
    private final String key;
    private final File file;

    public boolean upload() throws RepoCleanerException {
        try {
            PutObjectRequest request = new PutObjectRequest(bucket, key, file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("binary/octet-stream");
            request.setMetadata(metadata);
            S3Info.CLIENT.putObject(request);
            return true;
        } catch (SdkClientException e) {
            throw new RepoCleanerException("Failed to upload file to S3", e);
        }
    }
}
