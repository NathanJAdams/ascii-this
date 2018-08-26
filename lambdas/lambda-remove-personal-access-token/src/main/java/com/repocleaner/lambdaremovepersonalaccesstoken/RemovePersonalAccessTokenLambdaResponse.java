package com.repocleaner.lambdaremovepersonalaccesstoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RemovePersonalAccessTokenLambdaResponse {
    public static final RemovePersonalAccessTokenLambdaResponse SUCCESS = new RemovePersonalAccessTokenLambdaResponse(true, null);

    private final boolean success;
    private final String error;
}
