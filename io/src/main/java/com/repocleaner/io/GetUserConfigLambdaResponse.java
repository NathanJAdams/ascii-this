package com.repocleaner.io;

import com.repocleaner.model.config.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetUserConfigLambdaResponse {
    public static final GetUserConfigLambdaResponse EMPTY_RESPONSE = new GetUserConfigLambdaResponse(null);

    private final Config config;
}
