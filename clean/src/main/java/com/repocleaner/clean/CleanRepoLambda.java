package com.repocleaner.clean;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.s3.S3FileTransferer;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;
import java.util.List;

public class CleanRepoLambda implements RequestHandler<S3Event, Void> {
    public Void handleRequest(S3Event event, Context context) {
        File folder = new File("/tmp");
        List<File> transferredFiles = S3FileTransferer.transfer(event, folder);
        for (File transferredFile : transferredFiles) {
            try {
                Cleaner.clean(transferredFile);
            } catch (RepoCleanerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
