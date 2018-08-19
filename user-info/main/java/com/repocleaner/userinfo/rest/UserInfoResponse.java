package com.repocleaner.userinfo.rest;

import lombok.Getter;
import com.repocleaner.userinfo.domain.UserInfo;

@Getter
public class UserInfoResponse {
    public static final UserInfoResponse EMPTY_RESPONSE = new UserInfoResponse(null);

    private final UserInfo userInfo;

    public UserInfoResponse(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
