package com.repocleaner.prepare;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LambdaResponse {
    public static final LambdaResponse SERVER_ERROR_RESPONSE = new LambdaResponse(false, "Server Error", null);

    private final boolean success;
    private final String message;
    private final ApiResponse apiResponse;
}
