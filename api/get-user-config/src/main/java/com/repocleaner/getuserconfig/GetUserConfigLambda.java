package com.repocleaner.getuserconfig;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.config.UserConfig;
import com.repocleaner.getuserconfig.rest.UserConfigRequest;
import com.repocleaner.getuserconfig.rest.UserConfigResponse;

public class GetUserConfigLambda implements RequestHandler<UserConfigRequest, UserConfigResponse> {
    public UserConfigResponse handleRequest(UserConfigRequest request, Context context) {
        String jwt = request.getJwt();
        UserConfig userConfig = UserConfigGateway.getUserConfig(jwt);
        return new UserConfigResponse(userConfig);
    }
}
