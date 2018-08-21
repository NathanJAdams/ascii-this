package com.repocleaner.send;

import com.repocleaner.s3.S3FileDeleter;
import com.repocleaner.s3.S3FileDownloader;
import com.repocleaner.sinkinfo.Sink;
import com.repocleaner.util.GsonUtil;
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
            File zippedFile = fileStructure.getZippedFile();
            File tempFile = fileStructure.getTempFile();

            S3FileDownloader.download(bucket, key, zippedFile);
            ZipUtil.extract(zippedFile, rootFolder);
            String sinkJson = IOUtils.toString(sinkFile, StandardCharsets.UTF_8);
            Sink sink = GsonUtil.fromJsonOrNull(sinkJson, Sink.class);
            String initiatorJson = IOUtils.toString(initiatorFile, StandardCharsets.UTF_8);
            Initiator initiator = GsonUtil.fromJsonOrNull(initiatorJson, Initiator.class);
            sink.sendSource(sourceFolder, tempFile);
            // TODO charge initiator for token cost
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to send cleaned repo", e);
        } finally {
            S3FileDeleter.delete(bucket, key);
        }
    }
}
