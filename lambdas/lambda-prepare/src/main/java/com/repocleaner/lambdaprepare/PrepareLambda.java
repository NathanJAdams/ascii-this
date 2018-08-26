package com.repocleaner.lambdaprepare;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.config.Config;
import com.repocleaner.initiator.Initiator;
import com.repocleaner.prepare.ApiResponse;
import com.repocleaner.prepare.Preparer;
import com.repocleaner.source.Source;
import com.repocleaner.util.RepoCleanerException;

public class PrepareLambda implements RequestHandler<PrepareLambdaRequest, PrepareLambdaResponse> {
    public PrepareLambdaResponse handleRequest(PrepareLambdaRequest request, Context context) {
        String requestId = context.getAwsRequestId();
        Initiator initiator = request.getInitiator();
        Config config = request.getConfig();
        Source source = request.getSource();
        try {
            ApiResponse apiResponse = Preparer.prepare(requestId, initiator, config, source);
            if (!initiator.requiresApiResponse()) {
                apiResponse = null;
            }
            return new PrepareLambdaResponse(true, "ok", apiResponse);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return PrepareLambdaResponse.SERVER_ERROR_RESPONSE;
        }
    }
}
