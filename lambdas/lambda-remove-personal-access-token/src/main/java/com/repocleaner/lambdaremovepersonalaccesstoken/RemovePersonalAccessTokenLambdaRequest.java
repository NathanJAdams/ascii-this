package com.repocleaner.lambdaremovepersonalaccesstoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RemovePersonalAccessTokenLambdaRequest {
    private final String jwt;
    private final String host;
    private final String account;
    private final String token;
}
