package com.repocleaner.lambdaaddpersonalaccesstoken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.apiaddpersonalaccesstoken.AddPersonalAccessToken;
import com.repocleaner.io.AddPersonalAccessTokenLambdaRequest;
import com.repocleaner.io.AddPersonalAccessTokenLambdaResponse;
import com.repocleaner.util.RepoCleanerException;

public class AddPersonalAccessTokenLambda implements RequestHandler<AddPersonalAccessTokenLambdaRequest, AddPersonalAccessTokenLambdaResponse> {
    public AddPersonalAccessTokenLambdaResponse handleRequest(AddPersonalAccessTokenLambdaRequest request, Context context) {
        String jwt = request.getJwt();
        String host = request.getHost();
        String account = request.getAccount();
        String token = request.getToken();
        try {
            AddPersonalAccessToken.addPersonalAccessToken(jwt, host, account, token);
            return com.repocleaner.io.AddPersonalAccessTokenLambdaResponse.SUCCESS;
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return new com.repocleaner.io.AddPersonalAccessTokenLambdaResponse(false, e.getMessage());
        }
    }
}
