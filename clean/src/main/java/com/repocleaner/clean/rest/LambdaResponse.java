package com.repocleaner.clean.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LambdaResponse {
    public static final LambdaResponse BAD_INPUT_RESPONSE = new LambdaResponse(false, "Bad Input");
    public static final LambdaResponse SERVER_ERROR_RESPONSE = new LambdaResponse(false, "Server Error");
    public static final LambdaResponse SUCCESS_RESPONSE = new LambdaResponse(true, "Success");

    private final boolean success;
    private final String message;
}