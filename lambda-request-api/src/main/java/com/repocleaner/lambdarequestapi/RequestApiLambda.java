package com.repocleaner.lambdarequestapi;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.core.io.RequestIO;
import com.repocleaner.core.io.UserIO;
import com.repocleaner.core.model.Sink;
import com.repocleaner.core.model.Source;
import com.repocleaner.core.util.Constants;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.core.util.json.JsonUtil;
import com.repocleaner.core.util.rest.ResponseUtil;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.s3.S3RequestIO;
import com.repocleaner.secrets.SecretCommander;

public class RequestApiLambda implements RequestHandler<RequestApiRequest, RequestApiResponse> {
    private static final UserIO USER_IO;
    private static final RequestIO REQUEST_IO;
    private static final ApiRequester API_REQUESTER = new ApiRequester();

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
    public RequestApiResponse handleRequest(RequestApiRequest request, Context context) {
        request.preCheck();
        String userId = request.getUserId();
        String token = request.getToken();
        Source source = request.getSource();
        Sink sink = request.getSink();
        String id;
        try {
            id = API_REQUESTER.request(USER_IO, REQUEST_IO, userId, token, source, sink);
            return new RequestApiResponse(id);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.internalError("Schedule clean");
        }
    }
}
