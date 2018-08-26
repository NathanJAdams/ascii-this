package com.repocleaner.lambdaremovepersonalaccesstoken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.apiremovepersonalaccesstoken.RemovePersonalAccessToken;
import com.repocleaner.util.RepoCleanerException;

public class RemovePersonalAccessTokenLambda implements RequestHandler<RemovePersonalAccessTokenLambdaRequest, RemovePersonalAccessTokenLambdaResponse> {
    public RemovePersonalAccessTokenLambdaResponse handleRequest(RemovePersonalAccessTokenLambdaRequest request, Context context) {
        String jwt = request.getJwt();
        String host = request.getHost();
        String account = request.getAccount();
        String token = request.getToken();
        try {
            RemovePersonalAccessToken.removePersonalAccessToken(jwt, host, account, token);
            return RemovePersonalAccessTokenLambdaResponse.SUCCESS;
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return new RemovePersonalAccessTokenLambdaResponse(false, e.getMessage());
        }
    }
}
