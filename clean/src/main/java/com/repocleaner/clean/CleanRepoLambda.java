package com.repocleaner.clean;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.s3.S3EventConsumer;

public class CleanRepoLambda implements RequestHandler<S3Event, Void> {
    public Void handleRequest(S3Event event, Context context) {
        S3EventConsumer.consume(event, Cleaner::clean);
        return null;
    }
}
