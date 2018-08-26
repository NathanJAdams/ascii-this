package com.repocleaner.lambdaaddhostedaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddHostedAccountLambdaResponse {
    public static final AddHostedAccountLambdaResponse SUCCESS = new AddHostedAccountLambdaResponse(true, null);

    private final boolean success;
    private final String error;
}
