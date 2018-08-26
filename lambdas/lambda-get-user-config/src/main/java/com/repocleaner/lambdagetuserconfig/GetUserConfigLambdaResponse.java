package com.repocleaner.lambdagetuserconfig;

import com.repocleaner.config.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetUserConfigLambdaResponse {
    public static final GetUserConfigLambdaResponse EMPTY_RESPONSE = new GetUserConfigLambdaResponse(null);

    private final Config config;
}
