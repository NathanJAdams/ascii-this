package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.repocleaner.util.RepoCleanerException;

import java.util.List;

public class S3EventUnwrapper {
    public static S3FileStructure download(S3Event event) throws RepoCleanerException {
        List<S3EventNotification.S3EventNotificationRecord> records = event.getRecords();
        S3EventNotification.S3EventNotificationRecord record = records.get(0);
        S3EventNotification.S3Entity s3 = record.getS3();
        String bucket = s3.getBucket().getName();
        String key = s3.getObject().getKey();
        S3FileStructure fileStructure = new S3FileStructure(key);
        fileStructure.download(bucket);
        return fileStructure;
    }
}
