package com.repocleaner.lambdaprepare;

import com.repocleaner.prepare.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PrepareLambdaResponse {
    public static final PrepareLambdaResponse SERVER_ERROR_RESPONSE = new PrepareLambdaResponse(false, "Server Error", null);

    private final boolean success;
    private final String message;
    private final ApiResponse apiResponse;
}
