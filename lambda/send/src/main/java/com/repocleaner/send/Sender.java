package com.repocleaner.send;

import com.repocleaner.initiator.Initiator;
import com.repocleaner.s3.S3FileDeleter;
import com.repocleaner.s3.S3FileDownloader;
import com.repocleaner.source.Source;
import com.repocleaner.util.FileStructure;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.JsonUtil;

import java.io.File;

public class Sender {
    private static final JsonUtil JSON_UTIL = new JsonUtil();

    public static void send(String bucket, String key) throws RepoCleanerException {
        try (FileStructure fileStructure = new FileStructure(key)) {
            File rootFolder = fileStructure.getRootFolder();
            File codeFolder = fileStructure.getCodeFolder();
            File initiatorFile = fileStructure.getInitiatorFile();
            // TODO
            File tokenFile = fileStructure.getTokenFile();
            File tokenCostFile = fileStructure.getTokenCostFile();
            File zippedFile = fileStructure.getZippedFile();
            File tempFile = fileStructure.getTempFile();

            S3FileDownloader.download(bucket, key, zippedFile);
            ZipUtil.extract(zippedFile, rootFolder);

            Initiator initiator = JSON_UTIL.fromJsonFileOrNull(initiatorFile, Initiator.class);
            Source source = JSON_UTIL.fromJsonFileOrNull(codeFolder, Source.class);

            source.sendCleaned(codeFolder, tempFile);
            // TODO charge initiator for token cost

            initiator.notifyAvailable(key);
        } finally {
            S3FileDeleter.delete(bucket, key);
        }
    }
}
