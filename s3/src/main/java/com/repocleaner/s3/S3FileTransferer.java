package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class S3FileTransferer {
    public static List<File> transfer(S3Event event, File folder) {
        List<S3EventNotification.S3EventNotificationRecord> records = event.getRecords();
        List<File> files = new ArrayList<>();
        AmazonS3 s3Client = AmazonS3Client.builder().build();
        for (S3EventNotification.S3EventNotificationRecord record : records) {
            S3EventNotification.S3Entity s3 = record.getS3();
            String bucket = s3.getBucket().getName();
            File bucketFolder = new File(folder, bucket);
            if (bucketFolder.mkdirs()) {
                try {
                    String key = getDecodedKey(s3.getObject().getKey());
                    File objectFile = new File(bucketFolder, key);
                    S3Object object = s3Client.getObject(bucket, key);
                    S3ObjectInputStream inputStream = object.getObjectContent();
                    copyContents(inputStream, objectFile);
                    files.add(objectFile);
                } catch (RepoCleanerException e) {
                    e.printStackTrace();
                }
            }
        }
        return files;
    }

    private static String getDecodedKey(String key) throws RepoCleanerException {
        key = key.replace('+', ' ');
        try {
            return URLDecoder.decode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RepoCleanerException("Failed to decode key", e);
        }
    }

    private static void copyContents(InputStream is, File outputFile) throws RepoCleanerException {
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            IOUtils.copy(is, bos);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to copy cleaned repo to sent file", e);
        }
    }
}
