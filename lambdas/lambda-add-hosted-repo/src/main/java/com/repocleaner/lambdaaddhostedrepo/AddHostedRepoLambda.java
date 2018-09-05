package com.repocleaner.lambdaaddhostedrepo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.apiaddhostedrepo.AddHostedRepo;
import com.repocleaner.io.AddHostedRepoLambdaRequest;
import com.repocleaner.io.AddHostedRepoLambdaResponse;
import com.repocleaner.model.user.HostedRepo;
import com.repocleaner.util.LocalDateTimeUtil;
import com.repocleaner.util.RepoCleanerException;

import java.time.LocalDateTime;

public class AddHostedRepoLambda implements RequestHandler<AddHostedRepoLambdaRequest, AddHostedRepoLambdaResponse> {
    public AddHostedRepoLambdaResponse handleRequest(AddHostedRepoLambdaRequest request, Context context) {
        String jwt = request.getJwt();
        String userEmail = request.getUserEmail();
        String host = request.getHost();
        String userName = request.getUserName();
        String repo=request.getRepo();
        String masterBranch=request.getMasterBranch();
        boolean isActive=true;
        String nextCleanTimeHex=LocalDateTimeUtil.toHex(LocalDateTime.now());
        HostedRepo hostedRepo = new HostedRepo(userEmail,host, userName, repo,masterBranch,isActive, nextCleanTimeHex);
        try {
            if (AddHostedRepo.addHostedRepo(jwt, hostedRepo)) {
                return AddHostedRepoLambdaResponse.SUCCESS;
            } else {
                return new AddHostedRepoLambdaResponse(false, "Failed to add hosted repo");
            }
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return new AddHostedRepoLambdaResponse(false, e.getMessage());
        }
    }
}
