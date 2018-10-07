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
import com.repocleaner.util.RepoCleanerException;

public class CronLambda implements RequestHandler<S3Event, Void> {
    private static final UserIO USER_IO;
    private static final CronIO CRON_IO;

    static {
        String serviceAccountKey = SecretCommander.getParameter(Constants.SECRET_ID_SERVICE_ACCOUNT_KEY);
        UserIO userIO = null;
        try {
            userIO = FirebaseUserIO.fromJson(serviceAccountKey);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        USER_IO = userIO;
        CRON_IO = new S3CronIO();
    }

    @Override
    public Void handleRequest(S3Event event, Context context) {
        CronApi.startCleanJobs(USER_IO, CRON_IO);
        return null;
    }
}
