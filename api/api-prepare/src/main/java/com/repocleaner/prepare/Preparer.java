package com.repocleaner.prepare;

import com.repocleaner.model.api.ApiResponse;
import com.repocleaner.model.user.Config;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.source.Source;
import com.repocleaner.s3.S3FileUploader;
import com.repocleaner.s3.S3Info;
import com.repocleaner.util.FileStructure;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.JsonUtil;

import java.io.File;
import java.util.UUID;

public class Preparer {
    private static final JsonUtil JSON_UTIL = new JsonUtil();

    public static ApiResponse prepare(String requestId, Initiator initiator, Config config, Source source) throws RepoCleanerException {
        preCheck(initiator);
        try (FileStructure fileStructure = new FileStructure(requestId)) {
            File rootFolder = fileStructure.getRootFolder();
            File codeFolder = fileStructure.getCodeFolder();
            File initiatorFile = fileStructure.getInitiatorFile();
            File configFile = fileStructure.getConfigFile();
            File sourceFile = fileStructure.getSourceFile();
            File tokenFile = fileStructure.getTokenFile();
            File zippedFile = fileStructure.getZippedFile();

            String key = UUID.randomUUID().toString();
            String token = UUID.randomUUID().toString();
            source.saveSource(codeFolder);

            JSON_UTIL.toJsonFile(initiator, initiatorFile);
            JSON_UTIL.toJsonFile(config, configFile);
            JSON_UTIL.toJsonFile(source, sourceFile);
            JSON_UTIL.toJsonFile(token, tokenFile);
            ZipUtil.zip(rootFolder, zippedFile);
            S3FileUploader.upload(S3Info.PREPARED_BUCKET, key, zippedFile);
            return new ApiResponse(key, token);
        }
    }

    private static void preCheck(Initiator initiator) throws RepoCleanerException {
        if (initiator.getMaxCredits() <= 0) {
            throw new RepoCleanerException("No tokens remaining");
        }
        if (!initiator.isPossibleToClean()) {
            throw new RepoCleanerException("Initiator vetoed request");
        }
    }
}
