package com.repocleaner.prepare;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.config.Config;
import com.repocleaner.initiator.Initiator;
import com.repocleaner.prepare.rest.LambdaRequest;
import com.repocleaner.prepare.rest.LambdaResponse;
import com.repocleaner.source.Source;
import com.repocleaner.util.RepoCleanerException;

public class PrepareRepoLambda implements RequestHandler<LambdaRequest, LambdaResponse> {
    public LambdaResponse handleRequest(LambdaRequest request, Context context) {
        String requestId = context.getAwsRequestId();
        Initiator initiator = request.getInitiator();
        Config config = request.getConfig();
        Source<?> source = request.getSource();
        try {
            ApiResponse apiResponse = Preparer.prepare(requestId, initiator, config,source);
            if (!initiator.requiresApiResponse()) {
                apiResponse = null;
            }
            return new LambdaResponse(true, "ok", apiResponse);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return LambdaResponse.SERVER_ERROR_RESPONSE;
        }
    }
}
