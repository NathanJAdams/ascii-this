package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.repocleaner.model.FileStructure;
import com.repocleaner.util.Constants;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.JsonUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class S3FileStructure {
    private static final Charset UTF_8 = Charset.forName("UTF-8");

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

    public static String downloadDiff(String id) throws RepoCleanerException {
        File zippedDiffFolder = new File(S3Info.TMP_FOLDER, "zippedDiff_" + id);
        File unzippedDiffFolder = new File(S3Info.TMP_FOLDER, "unzippedDiff_" + id);
        S3Commander.download(Constants.BUCKET_WAITING, id, zippedDiffFolder);
        ZipUtil.extract(zippedDiffFolder, unzippedDiffFolder);
        File file = new File(unzippedDiffFolder, Constants.DIFF_FILE);
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            return IOUtils.toString(bis, UTF_8);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to read diff", e);
        } finally {
            file.delete();
            unzippedDiffFolder.delete();
            zippedDiffFolder.delete();
        }
    }
}
