package com.repocleaner.lambdasend;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.core.util.json.JsonUtil;
import com.repocleaner.s3.S3SendIO;
import com.repocleaner.sink.SinkGsonCustomiser;

public class SendLambda implements RequestHandler<S3Event, Void> {
    private static final Sender SENDER = new Sender();

    @Override
    public Void handleRequest(S3Event event, Context context) {
        JsonUtil jsonUtil = new JsonUtil(new SinkGsonCustomiser());
        try (S3SendIO sendIO = new S3SendIO(event, jsonUtil)) {
            SENDER.send(sendIO);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
