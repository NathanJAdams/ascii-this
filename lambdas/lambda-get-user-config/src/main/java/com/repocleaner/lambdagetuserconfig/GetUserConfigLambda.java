package com.repocleaner.lambdagetuserconfig;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.config.Config;
import com.repocleaner.getuserconfig.GetUserConfig;

public class GetUserConfigLambda implements RequestHandler<UserConfigRequest, UserConfigResponse> {
    public UserConfigResponse handleRequest(UserConfigRequest request, Context context) {
        String jwt = request.getJwt();
        Config config = GetUserConfig.getUserConfig(jwt);
        return new UserConfigResponse(config);
    }
}
