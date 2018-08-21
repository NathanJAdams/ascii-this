package com.repocleaner.prepare;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.prepare.initiator.Initiator;
import com.repocleaner.prepare.rest.LambdaRequest;
import com.repocleaner.prepare.rest.LambdaResponse;
import com.repocleaner.sourceinfo.Source;
import com.repocleaner.util.RepoCleanerException;

public class PrepareRepoLambda implements RequestHandler<LambdaRequest, LambdaResponse> {
    public LambdaResponse handleRequest(LambdaRequest request, Context context) {
        String requestId = context.getAwsRequestId();
        Initiator initiator = request.getInitiator();
        Source<?> source = request.getSource();
        try {
            Preparer.prepare(requestId, initiator, source);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return LambdaResponse.SERVER_ERROR_RESPONSE;
        }
        return LambdaResponse.SUCCESS_RESPONSE;
    }
}
