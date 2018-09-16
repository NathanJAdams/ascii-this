package com.repocleaner.io.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PrepareLambdaResponse {
    public static final PrepareLambdaResponse SERVER_ERROR_RESPONSE = new PrepareLambdaResponse(false, "Server Error", null, null);

    private final boolean success;
    private final String message;
    private final String id;
    private final String token;
}
