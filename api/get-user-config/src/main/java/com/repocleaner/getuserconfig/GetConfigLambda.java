package com.repocleaner.getuserconfig;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.config.Config;
import com.repocleaner.getuserconfig.rest.UserConfigRequest;
import com.repocleaner.getuserconfig.rest.UserConfigResponse;

public class GetConfigLambda implements RequestHandler<UserConfigRequest, UserConfigResponse> {
    public UserConfigResponse handleRequest(UserConfigRequest request, Context context) {
        String jwt = request.getJwt();
        Config config = ConfigGateway.getConfig(jwt);
        return new UserConfigResponse(config);
    }
}
