package com.repocleaner.lambdacron;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.apicron.CronApi;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.io.external.CronIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.s3.S3CronIO;
import com.repocleaner.secrets.SecretCommander;
import com.repocleaner.util.Constants;

import java.nio.charset.StandardCharsets;

public class CronLambda implements RequestHandler<S3Event, Void> {
    private static final UserIO USER_IO;
    private static final CronIO CRON_IO = new S3CronIO();

    static {
        String serviceAccountKey = SecretCommander.getSecretAsString(Constants.SECRET_ID_SERVICE_ACCOUNT_KEY);
        byte[] serviceAccountKeyContents = serviceAccountKey.getBytes(StandardCharsets.UTF_8);
        USER_IO = new FirebaseUserIO(serviceAccountKeyContents);
    }

    @Override
    public Void handleRequest(S3Event event, Context context) {
        CronApi.startCleanJobs(USER_IO, CRON_IO);
        return null;
    }
}
