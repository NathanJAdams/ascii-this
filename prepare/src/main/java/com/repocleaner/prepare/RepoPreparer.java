package com.repocleaner.prepare;

import com.repocleaner.prepare.initiator.Initiator;
import com.repocleaner.sourceinfo.Source;
import com.repocleaner.util.GsonUtil;
import com.repocleaner.util.RepoCleanerException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RepoPreparer {
    public static void storeRequest(String requestId, Initiator initiator, Source source) throws RepoCleanerException {
        if (initiator.getTokens() <= 0) {
            throw new RepoCleanerException("No tokens remaining");
        }
        if (!initiator.isPossibleToClean()) {
            throw new RepoCleanerException("Initiator vetoed as not possible");
        }
        File requestFolder = new File("/tmp/" + requestId);
        File sourceFolder = new File(requestFolder, "source");
        File sinkDetailsFile = new File(requestFolder, "sink.json");
        if (!sourceFolder.mkdirs()) {
            throw new RepoCleanerException("Failed to create file structure to store repo");
        }
        Object sinkDetails = source.saveSourceGetSinkDetails(sourceFolder);
        try (FileOutputStream fos = new FileOutputStream(sinkDetailsFile);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             OutputStreamWriter writer = new OutputStreamWriter(bos)) {
            String sinkJson = GsonUtil.toJson(sinkDetails);
            if (sinkJson == null) {
                throw new RepoCleanerException("Failed to create sink details");
            }
            writer.write(sinkJson);
        } catch (IOException e) {
            sourceFolder.delete();
            sinkDetailsFile.delete();
            throw new RepoCleanerException("Failed to write sink details", e);
        }
        // TODO send request folder to S3 then delete it
    }
}
