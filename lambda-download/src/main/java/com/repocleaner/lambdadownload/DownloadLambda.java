package com.repocleaner.lambdadownload;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.core.util.json.JsonUtil;
import com.repocleaner.s3.S3DownloadIO;
import com.repocleaner.source.SourceGsonCustomiser;

public class DownloadLambda implements RequestHandler<S3Event, Void> {
    private static final Downloader DOWNLOADER = new Downloader();

    public Void handleRequest(S3Event event, Context context) {
        JsonUtil jsonUtil = new JsonUtil(new SourceGsonCustomiser());
        try (S3DownloadIO downloadIO = new S3DownloadIO(event, jsonUtil)) {
            DOWNLOADER.download(downloadIO);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
