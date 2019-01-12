package com.repocleaner.lambdarequestapi;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.corerequesterapi.ApiRequester;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.io.RequestIO;
import com.repocleaner.io.UserIO;
import com.repocleaner.json.JsonInfo;
import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import com.repocleaner.s3.S3RequestIO;
import com.repocleaner.secrets.SecretCommander;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.rest.ResponseUtil;

public class RequestApiLambda implements RequestHandler<RequestApiRequest, RequestApiResponse> {
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
    public RequestApiResponse handleRequest(RequestApiRequest request, Context context) {
        request.preCheck();
        String userId = request.getUserId();
        String token = request.getToken();
        Source source = request.getSource();
        Sink sink = request.getSink();
        String id;
        try {
            id = ApiRequester.schedule(USER_IO, SCHEDULE_IO, userId, token, source, sink);
            return new RequestApiResponse(id);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.internalError("Schedule clean");
        }
    }
}
