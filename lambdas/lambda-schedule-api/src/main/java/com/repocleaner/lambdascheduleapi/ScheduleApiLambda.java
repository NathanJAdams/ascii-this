package com.repocleaner.lambdascheduleapi;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.corescheduleapi.ApiScheduler;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.io.external.ScheduleIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.io.rest.ResponseInfo;
import com.repocleaner.io.rest.ScheduleApiRequest;
import com.repocleaner.io.rest.ScheduleApiResponse;
import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import com.repocleaner.s3.S3ScheduleIO;
import com.repocleaner.secrets.SecretCommander;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;

public class ScheduleApiLambda implements RequestHandler<ScheduleApiRequest, ScheduleApiResponse> {
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
    public ScheduleApiResponse handleRequest(ScheduleApiRequest request, Context context) {
        String userId = request.getUserId();
        String token = request.getToken();
        Source source = request.getSource();
        Sink sink = request.getSink();
        boolean success;
        String message;
        String id;
        try {
            id = ApiScheduler.schedule(USER_IO, SCHEDULE_IO, userId, token, source, sink);
            success = true;
            message = "Successfully scheduled clean";
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            success = false;
            message = "Failed to schedule clean";
            id = null;
        }
        ResponseInfo responseInfo = new ResponseInfo(success, message);
        return new ScheduleApiResponse(responseInfo, id);
    }
}
