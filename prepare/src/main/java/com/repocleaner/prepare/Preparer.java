package com.repocleaner.prepare;

import com.repocleaner.prepare.initiator.Initiator;
import com.repocleaner.s3.S3FileUploader;
import com.repocleaner.s3.S3Info;
import com.repocleaner.sourceinfo.Source;
import com.repocleaner.util.GsonUtil;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.filestructure.FileStructureUtil;
import com.repocleaner.util.filestructure.PrepareFileStructure;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Preparer {
    public static void prepare(String requestId, Initiator initiator, Source<?> source) throws RepoCleanerException {
        preCheck(initiator);
        try (PrepareFileStructure fileStructure = FileStructureUtil.createPrepareFileStructure(requestId)) {
            File requestFolder = fileStructure.getRootFolder();
            File initiatorFile = fileStructure.getInitiatorFile();
            File sourceFolder = fileStructure.getSourceFolder();
            File sinkFile = fileStructure.getSinkFile();
            File zippedFile = fileStructure.getZippedFile();

            saveJson(initiator, initiatorFile);
            Object sink = source.saveSourceGetSink(sourceFolder);
            saveJson(sink, sinkFile);
            ZipUtil.zip(requestFolder, zippedFile);
            String randomKeyName = UUID.randomUUID().toString();
            S3FileUploader.upload(S3Info.PREPARED_BUCKET, randomKeyName, zippedFile);
        }
    }

    private static void preCheck(Initiator initiator) throws RepoCleanerException {
        if (initiator.getTokens() <= 0) {
            throw new RepoCleanerException("No tokens remaining");
        }
        if (!initiator.isPossibleToClean()) {
            throw new RepoCleanerException("Initiator vetoed request");
        }
    }

    private static <T> void saveJson(T object, File file) throws RepoCleanerException {
        String json = GsonUtil.toJson(object);
        if (json == null) {
            throw new RepoCleanerException("Failed to create json");
        }
        try {
            IOUtils.saveToFile(json, file);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to save json", e);
        }
    }
}
