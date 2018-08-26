package com.repocleaner.lambdaaddhostedaccount;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.apiremovehostedaccount.RemoveHostedAccount;
import com.repocleaner.util.RepoCleanerException;

public class RemoveHostedAccountLambda implements RequestHandler<RemoveHostedAccountLambdaRequest, RemoveHostedAccountLambdaResponse> {
    public RemoveHostedAccountLambdaResponse handleRequest(RemoveHostedAccountLambdaRequest request, Context context) {
        String jwt = request.getJwt();
        String host = request.getHost();
        String account = request.getAccount();
        try {
            RemoveHostedAccount.removeHostedAccount(jwt, host, account);
            return RemoveHostedAccountLambdaResponse.SUCCESS;
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return new RemoveHostedAccountLambdaResponse(false, e.getMessage());
        }
    }
}
