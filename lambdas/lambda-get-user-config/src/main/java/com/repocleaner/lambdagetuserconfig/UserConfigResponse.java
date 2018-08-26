package com.repocleaner.lambdagetuserconfig;

import com.repocleaner.config.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserConfigResponse {
    public static final UserConfigResponse EMPTY_RESPONSE = new UserConfigResponse(null);

    private final Config config;
}
