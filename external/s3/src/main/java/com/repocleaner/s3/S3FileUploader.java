package com.repocleaner.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public class S3FileUploader {
    public static void upload(String bucket, String key, File file) throws RepoCleanerException {
        try {
            PutObjectRequest request = new PutObjectRequest(bucket, key, file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("binary/octet-stream");
            request.setMetadata(metadata);
            S3Info.CLIENT.putObject(request);
        } catch (SdkClientException e) {
            throw new RepoCleanerException("Failed to upload file to S3", e);
        }
    }
}
