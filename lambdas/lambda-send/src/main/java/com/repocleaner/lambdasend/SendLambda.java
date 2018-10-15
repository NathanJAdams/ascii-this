package com.repocleaner.lambdasend;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.coresend.Sender;
import com.repocleaner.json.JsonInfo;
import com.repocleaner.s3.S3SendIO;
import com.repocleaner.util.RepoCleanerException;

public class SendLambda implements RequestHandler<S3Event, Void> {
    @Override
    public Void handleRequest(S3Event event, Context context) {
        try (S3SendIO sendIO = new S3SendIO(event, JsonInfo.JSON_UTIL)) {
            Sender.send(sendIO);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
