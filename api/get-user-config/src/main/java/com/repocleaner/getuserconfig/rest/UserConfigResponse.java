package com.repocleaner.getuserconfig.rest;

import com.repocleaner.config.UserConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserConfigResponse {
    public static final UserConfigResponse EMPTY_RESPONSE = new UserConfigResponse(null);

    private final UserConfig userConfig;
}
