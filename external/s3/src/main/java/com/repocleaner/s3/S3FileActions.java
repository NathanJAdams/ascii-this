package com.repocleaner.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;
import java.io.IOException;

public class S3FileActions {
    public static void download(String bucket, String key, File file) throws RepoCleanerException {
        try {
            S3Object object = S3Info.CLIENT.getObject(bucket, key);
            S3ObjectInputStream inputStream = object.getObjectContent();
            IOUtils.copy(inputStream, file);
        } catch (SdkClientException | IOException e) {
            throw new RepoCleanerException("Failed to download file from S3", e);
        }
    }

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

    public static void delete(String bucket, String key) throws RepoCleanerException {
        try {
            DeleteObjectRequest request = new DeleteObjectRequest(bucket, key);
            S3Info.CLIENT.deleteObject(request);
        } catch (SdkClientException e) {
            throw new RepoCleanerException("Failed to delete file in S3", e);
        }
    }
}
