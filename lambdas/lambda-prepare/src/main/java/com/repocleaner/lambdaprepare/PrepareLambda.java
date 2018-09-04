package com.repocleaner.lambdaprepare;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.model.api.ApiResponse;
import com.repocleaner.io.PrepareLambdaRequest;
import com.repocleaner.io.PrepareLambdaResponse;
import com.repocleaner.model.config.Config;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.source.Source;
import com.repocleaner.prepare.Preparer;
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
