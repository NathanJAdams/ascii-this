package com.repocleaner.lambdaaddhostedaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RemoveHostedAccountLambdaResponse {
    public static final RemoveHostedAccountLambdaResponse SUCCESS = new RemoveHostedAccountLambdaResponse(true, null);

    private final boolean success;
    private final String error;
}
