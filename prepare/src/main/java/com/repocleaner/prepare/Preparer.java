package com.repocleaner.prepare;

import com.repocleaner.config.Config;
import com.repocleaner.initiator.Initiator;
import com.repocleaner.s3.S3FileUploader;
import com.repocleaner.s3.S3Info;
import com.repocleaner.source.Source;
import com.repocleaner.util.JsonUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.filestructure.FileStructureUtil;
import com.repocleaner.util.filestructure.PrepareFileStructure;

import java.io.File;
import java.util.UUID;

public class Preparer {
    public static ApiResponse prepare(String requestId, Initiator initiator, Config config, Source<?> source) throws RepoCleanerException {
        preCheck(initiator);
        try (PrepareFileStructure fileStructure = FileStructureUtil.createPrepareFileStructure(requestId)) {
            File rootFolder = fileStructure.getRootFolder();
            File initiatorFile = fileStructure.getInitiatorFile();
            File configFile = fileStructure.getConfigFile();
            File sourceFolder = fileStructure.getSourceFolder();
            File sinkFile = fileStructure.getSinkFile();
            File tokenFile = fileStructure.getTokenFile();
            File zippedFile = fileStructure.getZippedFile();

            String key = UUID.randomUUID().toString();
            String token = UUID.randomUUID().toString();
            Object sink = source.saveSourceGetSink(sourceFolder);

            JsonUtil.toJsonFile(initiator, initiatorFile);
            JsonUtil.toJsonFile(config, configFile);
            JsonUtil.toJsonFile(sink, sinkFile);
            JsonUtil.toJsonFile(token, tokenFile);
            ZipUtil.zip(rootFolder, zippedFile);
            S3FileUploader.upload(S3Info.PREPARED_BUCKET, key, zippedFile);
            return new ApiResponse(key, token);
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
}
