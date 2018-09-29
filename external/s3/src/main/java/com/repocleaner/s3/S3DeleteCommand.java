package com.repocleaner.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3DeleteCommand {
    private final String bucket;
    private final String key;

    public boolean delete() throws RepoCleanerException {
        try {
            DeleteObjectRequest request = new DeleteObjectRequest(bucket, key);
            S3Info.CLIENT.deleteObject(request);
            return true;
        } catch (SdkClientException e) {
            throw new RepoCleanerException("Failed to delete file in S3", e);
        }
    }
}
