package com.repocleaner.lambdaaddhostedaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RemoveHostedAccountLambdaRequest {
    private final String jwt;
    private final String host;
    private final String account;
}
