package com.repocleaner.send;

import com.repocleaner.model.send.CleanedNotification;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.source.Source;
import com.repocleaner.s3.S3FileDeleter;
import com.repocleaner.s3.S3FileDownloader;
import com.repocleaner.util.CleanResult;
import com.repocleaner.util.FileStructure;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.JsonUtil;
import com.repocleaner.util.rest.RestRequest;
import com.repocleaner.util.rest.RestUtil;

import java.io.File;

public class Sender {
    private static final JsonUtil JSON_UTIL = new JsonUtil();

    public static void send(String bucket, String key) throws RepoCleanerException {
        try (FileStructure fileStructure = new FileStructure(key)) {
            File rootFolder = fileStructure.getRootFolder();
            File codeFolder = fileStructure.getCodeFolder();
            File initiatorFile = fileStructure.getInitiatorFile();
            File sourceFile = fileStructure.getSourceFile();
            File cleanResultFile = fileStructure.getCleanResultFile();
            File zippedFile = fileStructure.getZippedFile();
            File tempFile = fileStructure.getTempFile();

            S3FileDownloader.download(bucket, key, zippedFile);
            ZipUtil.extract(zippedFile, rootFolder);

            Initiator initiator = JSON_UTIL.fromJsonFileOrNull(initiatorFile, Initiator.class);
            Source source = JSON_UTIL.fromJsonFileOrNull(sourceFile, Source.class);
            CleanResult cleanResult = JSON_UTIL.fromJsonFileOrNull(cleanResultFile, CleanResult.class);

            source.sendCleaned(codeFolder, cleanResult, tempFile);
            // TODO charge initiator for token cost

            String notificationEndpoint = initiator.getNotificationEndpoint();
            if (notificationEndpoint != null) {
                CleanedNotification notification = new CleanedNotification(key);
                RestRequest<Void> request = RestRequest.POST(notificationEndpoint, notification, Void.class);
                RestUtil.getResponse(request);
            }
        } finally {
            S3FileDeleter.delete(bucket, key);
        }
    }
}
