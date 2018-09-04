package com.repocleaner.lambdacron;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.apicron.CronApi;

public class CronLambda implements RequestHandler<S3Event, Void> {
    @Override
    public Void handleRequest(S3Event event, Context context) {
        CronApi.startCleanJobs();
        return null;
    }
}
