package com.repocleaner.lambdasetrepotoken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.io.rest.SetRepoTokenLambdaRequest;
import com.repocleaner.io.rest.SetRepoTokenLambdaResponse;
import com.repocleaner.repotoken.RepoToken;
import com.repocleaner.secrets.SecretCommander;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;

public class SetRepoTokenLambda implements RequestHandler<SetRepoTokenLambdaRequest, SetRepoTokenLambdaResponse> {
    private static final RepoToken REPO_TOKEN = new RepoToken();
    private static final UserIO USER_IO;

    static {
        String serviceAccountKey = SecretCommander.getParameter(Constants.SECRET_ID_SERVICE_ACCOUNT_KEY);
        UserIO userIO = null;
        try {
            userIO = FirebaseUserIO.fromJson(serviceAccountKey);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        USER_IO = userIO;
    }

    @Override
    public SetRepoTokenLambdaResponse handleRequest(SetRepoTokenLambdaRequest request, Context context) {
        String userId = request.getUserId();
        String token = request.getToken();
        try {
            boolean success = REPO_TOKEN.encryptToken(USER_IO, userId, token);
            if (success) {
                return SetRepoTokenLambdaResponse.SUCCESS;
            } else {
                return new SetRepoTokenLambdaResponse(false, "Failed to set repo token");
            }
        } catch (RepoCleanerException e) {
            return new SetRepoTokenLambdaResponse(false, e.getMessage());
        }
    }
}
