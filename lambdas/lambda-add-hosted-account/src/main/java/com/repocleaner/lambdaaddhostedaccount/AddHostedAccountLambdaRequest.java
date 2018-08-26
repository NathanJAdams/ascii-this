package com.repocleaner.lambdaaddhostedaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddHostedAccountLambdaRequest {
    private final String jwt;
    private final String host;
    private final String account;
}
