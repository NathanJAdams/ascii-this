package com.repocleaner.lambdadownload;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.coredownload.Downloader;
import com.repocleaner.json.JsonInfo;
import com.repocleaner.s3.S3DownloadIO;
import com.repocleaner.util.RepoCleanerException;

public class DownloadLambda implements RequestHandler<S3Event, Void> {
    public Void handleRequest(S3Event event, Context context) {
        try {
            try (S3DownloadIO prepareIO = new S3DownloadIO(event, JsonInfo.JSON_UTIL)) {
                Downloader.download(prepareIO);
            }
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
