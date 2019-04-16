package com.repocleaner.lambdarequestcron;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.core.io.RequestIO;
import com.repocleaner.core.io.UserIO;
import com.repocleaner.core.util.Constants;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.core.util.json.JsonUtil;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.s3.S3RequestIO;
import com.repocleaner.secrets.SecretCommander;

public class RequestCronLambda implements RequestHandler<S3Event, Void> {
    private static final UserIO USER_IO;
    private static final RequestIO REQUEST_IO;
    private static final CronRequester CRON_REQUESTER = new CronRequester();

    static {
        String serviceAccountKey = SecretCommander.getParameter(Constants.SECRET_ID_SERVICE_ACCOUNT_KEY);
        UserIO userIO = null;
        try {
            userIO = new FirebaseUserIO(serviceAccountKey);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        USER_IO = userIO;
        REQUEST_IO = new S3RequestIO(new JsonUtil());
    }

    @Override
    public Void handleRequest(S3Event event, Context context) {
        try {
            CRON_REQUESTER.schedule(USER_IO, REQUEST_IO);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
