package com.repocleaner.io.rest;

import com.repocleaner.model.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetUserConfigLambdaResponse {
    public static final GetUserConfigLambdaResponse EMPTY_RESPONSE = new GetUserConfigLambdaResponse(null);

    private final Config config;
}
