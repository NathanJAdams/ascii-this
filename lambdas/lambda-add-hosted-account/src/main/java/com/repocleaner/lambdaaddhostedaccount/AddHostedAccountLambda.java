package com.repocleaner.lambdaaddhostedaccount;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.apiaddhostedaccount.AddHostedAccount;
import com.repocleaner.io.AddHostedAccountLambdaRequest;
import com.repocleaner.io.AddHostedAccountLambdaResponse;
import com.repocleaner.model.user.HostedAccount;
import com.repocleaner.model.user.HostedKey;
import com.repocleaner.util.RepoCleanerException;

public class AddHostedAccountLambda implements RequestHandler<AddHostedAccountLambdaRequest, AddHostedAccountLambdaResponse> {
    public AddHostedAccountLambdaResponse handleRequest(AddHostedAccountLambdaRequest request, Context context) {
        String jwt = request.getJwt();
        String userEmail = request.getUserEmail();
        String host = request.getHost();
        String userName = request.getUserName();
        HostedKey hostedKey = new HostedKey(userEmail, host, userName);
        String repoRegex = "*";
        HostedAccount hostedAccount = new HostedAccount(hostedKey, repoRegex);
        try {
            if (AddHostedAccount.addHostedAccount(jwt, hostedAccount)) {
                return AddHostedAccountLambdaResponse.SUCCESS;
            } else {
                return new AddHostedAccountLambdaResponse(false, "Failed to add hosted account");
            }
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return new AddHostedAccountLambdaResponse(false, e.getMessage());
        }
    }
}
