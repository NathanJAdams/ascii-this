package com.repocleaner.lambdagetuserconfig;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.config.Config;
import com.repocleaner.getuserconfig.GetUserConfig;
import com.repocleaner.util.RepoCleanerException;

public class GetUserConfigLambda implements RequestHandler<GetUserConfigLambdaRequest, GetUserConfigLambdaResponse> {
    public GetUserConfigLambdaResponse handleRequest(GetUserConfigLambdaRequest request, Context context) {
        String jwt = request.getJwt();
        try {
            Config config = GetUserConfig.getUserConfig(jwt);
            return new GetUserConfigLambdaResponse(config);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return GetUserConfigLambdaResponse.EMPTY_RESPONSE;
        }
    }
}
