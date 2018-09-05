package com.repocleaner.lambdaaddhostedrepo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.apiremovehostedrepo.RemoveHostedRepo;
import com.repocleaner.io.RemoveHostedRepoLambdaRequest;
import com.repocleaner.io.RemoveHostedRepoLambdaResponse;
import com.repocleaner.util.RepoCleanerException;

public class RemoveHostedRepoLambda implements RequestHandler<RemoveHostedRepoLambdaRequest, RemoveHostedRepoLambdaResponse> {
    public RemoveHostedRepoLambdaResponse handleRequest(RemoveHostedRepoLambdaRequest request, Context context) {
        String jwt = request.getJwt();
        String host = request.getHost();
        String account = request.getAccount();
        try {
            RemoveHostedRepo.removeHostedRepo(jwt, host, account);
            return RemoveHostedRepoLambdaResponse.SUCCESS;
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return new RemoveHostedRepoLambdaResponse(false, e.getMessage());
        }
    }
}
