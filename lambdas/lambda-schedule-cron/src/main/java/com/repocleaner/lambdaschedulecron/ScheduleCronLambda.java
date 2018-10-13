package com.repocleaner.lambdaschedulecron;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.coreschedulecron.CronScheduler;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.io.external.ScheduleIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.s3.S3ScheduleIO;
import com.repocleaner.secrets.SecretCommander;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;

public class ScheduleCronLambda implements RequestHandler<S3Event, Void> {
    private static final UserIO USER_IO;
    private static final ScheduleIO SCHEDULE_IO;

    static {
        String serviceAccountKey = SecretCommander.getParameter(Constants.SECRET_ID_SERVICE_ACCOUNT_KEY);
        UserIO userIO = null;
        try {
            userIO = FirebaseUserIO.fromJson(serviceAccountKey);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        USER_IO = userIO;
        SCHEDULE_IO = new S3ScheduleIO();
    }

    @Override
    public Void handleRequest(S3Event event, Context context) {
        try {
            CronScheduler.schedule(USER_IO, SCHEDULE_IO);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
