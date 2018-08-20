package com.repocleaner.getuserinfo.rest;

import com.repocleaner.userinfo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponse {
    public static final UserInfoResponse EMPTY_RESPONSE = new UserInfoResponse(null);

    private final UserInfo userInfo;
}
