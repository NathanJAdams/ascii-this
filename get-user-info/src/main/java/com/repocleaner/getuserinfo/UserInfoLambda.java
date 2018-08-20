package com.repocleaner.getuserinfo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.getuserinfo.rest.UserInfoRequest;
import com.repocleaner.getuserinfo.rest.UserInfoResponse;
import com.repocleaner.userinfo.UserInfo;

public class UserInfoLambda implements RequestHandler<UserInfoRequest, UserInfoResponse> {
    public UserInfoResponse handleRequest(UserInfoRequest request, Context context) {
        String jwt = request.getJwt();
        UserInfo userInfo = UserInfoGateway.getUserInfo(jwt);
        return new UserInfoResponse(userInfo);
    }
}
