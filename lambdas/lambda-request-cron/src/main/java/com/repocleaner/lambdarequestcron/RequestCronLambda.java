package com.repocleaner.lambdarequestcron;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.corerequestercron.CronRequester;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.io.RequestIO;
import com.repocleaner.io.UserIO;
import com.repocleaner.json.JsonInfo;
import com.repocleaner.s3.S3RequestIO;
import com.repocleaner.secrets.SecretCommander;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;

public class RequestCronLambda implements RequestHandler<S3Event, Void> {
    private static final UserIO USER_IO;
    private static final RequestIO SCHEDULE_IO;

    static {
        String serviceAccountKey = SecretCommander.getParameter(Constants.SECRET_ID_SERVICE_ACCOUNT_KEY);
        UserIO userIO = null;
        try {
            userIO = FirebaseUserIO.fromJson(serviceAccountKey);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        USER_IO = userIO;
        SCHEDULE_IO = new S3RequestIO(JsonInfo.JSON_UTIL);
    }

    @Override
    public Void handleRequest(S3Event event, Context context) {
        try {
            CronRequester.schedule(USER_IO, SCHEDULE_IO);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
