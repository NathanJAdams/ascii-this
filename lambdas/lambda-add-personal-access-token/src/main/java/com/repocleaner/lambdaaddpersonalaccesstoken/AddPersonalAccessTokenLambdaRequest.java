package com.repocleaner.lambdaaddpersonalaccesstoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddPersonalAccessTokenLambdaRequest {
    private final String jwt;
    private final String host;
    private final String account;
    private final String token;
}
