package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.repocleaner.util.RepoCleanerException;

import java.util.List;

public class S3EventConsumer {
    public static void consume(S3Event event, S3BucketKeyConsumer bucketKeyConsumer) {
        List<S3EventNotification.S3EventNotificationRecord> records = event.getRecords();
        for (S3EventNotification.S3EventNotificationRecord record : records) {
            S3EventNotification.S3Entity s3 = record.getS3();
            String bucket = s3.getBucket().getName();
            String key = s3.getObject().getKey();
            try {
                bucketKeyConsumer.consume(bucket, key);
            } catch (RepoCleanerException e) {
                e.printStackTrace();
            }
        }
    }
}
