package com.repocleaner.send;

import com.repocleaner.initiator.Initiator;
import com.repocleaner.s3.S3FileDeleter;
import com.repocleaner.s3.S3FileDownloader;
import com.repocleaner.sink.Sink;
import com.repocleaner.util.JsonUtil;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.filestructure.FileStructureUtil;
import com.repocleaner.util.filestructure.SendFileStructure;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Sender {
    public static void send(String bucket, String key) throws RepoCleanerException {
        try (SendFileStructure fileStructure = FileStructureUtil.createSendFileStructure(key)) {
            File rootFolder = fileStructure.getRootFolder();
            File initiatorFile = fileStructure.getInitiatorFile();
            File sourceFolder = fileStructure.getSourceFolder();
            File sinkFile = fileStructure.getSinkFile();
            File tokenFile = fileStructure.getTokenFile();
            File tokenCostFile = fileStructure.getTokenCostFile();
            File zippedFile = fileStructure.getZippedFile();
            File tempFile = fileStructure.getTempFile();

            S3FileDownloader.download(bucket, key, zippedFile);
            ZipUtil.extract(zippedFile, rootFolder);

            Initiator initiator = JsonUtil.fromJsonFileOrNull(initiatorFile, Initiator.class);
            Sink sink = JsonUtil.fromJsonFileOrNull(sinkFile, Sink.class);


            sink.sendSource(sourceFolder, tempFile);
            // TODO charge initiator for token cost

            initiator.notifyAvailable(key);
        } finally {
            S3FileDeleter.delete(bucket, key);
        }
    }
}
