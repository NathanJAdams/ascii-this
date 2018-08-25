package com.repocleaner.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.repocleaner.util.RepoCleanerException;

public class S3FileDeleter {
    public static void delete(String bucket, String key) throws RepoCleanerException {
        try {
            DeleteObjectRequest request = new DeleteObjectRequest(bucket, key);
            S3Info.CLIENT.deleteObject(request);
        } catch (SdkClientException e) {
            throw new RepoCleanerException("Failed to delete object from S3", e);
        }
    }
}
