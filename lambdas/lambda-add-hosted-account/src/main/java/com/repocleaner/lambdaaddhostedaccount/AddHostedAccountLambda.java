package com.repocleaner.lambdaaddhostedaccount;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.apiaddhostedaccount.AddHostedAccount;
import com.repocleaner.user.HostedAccount;
import com.repocleaner.util.RepoCleanerException;

import java.util.Collections;

public class AddHostedAccountLambda implements RequestHandler<AddHostedAccountLambdaRequest, AddHostedAccountLambdaResponse> {
    public AddHostedAccountLambdaResponse handleRequest(AddHostedAccountLambdaRequest request, Context context) {
        String jwt = request.getJwt();
        String host = request.getHost();
        String account = request.getAccount();
        HostedAccount hostedAccount = new HostedAccount(Collections.emptyMap(), "*", Collections.emptyMap());
        try {
            AddHostedAccount.addHostedAccount(jwt, host, account, hostedAccount);
            return AddHostedAccountLambdaResponse.SUCCESS;
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return new AddHostedAccountLambdaResponse(false, e.getMessage());
        }
    }
}
