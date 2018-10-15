package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.repocleaner.model.FileStructure;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.JsonUtil;

import java.io.File;
import java.util.List;

public class S3FileStructure {
    public static FileStructure download(S3Event event, JsonUtil jsonUtil) throws RepoCleanerException {
        List<S3EventNotification.S3EventNotificationRecord> records = event.getRecords();
        S3EventNotification.S3EventNotificationRecord record = records.get(0);
        S3EventNotification.S3Entity s3 = record.getS3();
        String bucket = s3.getBucket().getName();
        String key = s3.getObject().getKey();
        FileStructure fileStructure = new FileStructure(S3Info.TMP_FOLDER, key, jsonUtil);
        File rootFolder = fileStructure.getRootFolder();
        File zippedInputFolder = new File(S3Info.TMP_FOLDER, "zippedInput_" + key);
        S3Commander.download(bucket, key, zippedInputFolder);
        ZipUtil.extract(zippedInputFolder, rootFolder);
        S3Commander.delete(bucket, key);
        zippedInputFolder.delete();
        return fileStructure;
    }

    public static void upload(FileStructure fileStructure, String uploadBucket) throws RepoCleanerException {
        String key = fileStructure.getKey();
        File rootFolder = fileStructure.getRootFolder();
        File zippedOutputFolder = new File(S3Info.TMP_FOLDER, "zippedOutput_" + key);
        ZipUtil.zip(rootFolder, zippedOutputFolder);
        S3Commander.upload(uploadBucket, key, zippedOutputFolder);
        zippedOutputFolder.delete();
    }
}
