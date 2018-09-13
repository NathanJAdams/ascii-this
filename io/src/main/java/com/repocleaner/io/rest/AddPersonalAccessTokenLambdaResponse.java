package com.repocleaner.io.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddPersonalAccessTokenLambdaResponse {
    public static final AddPersonalAccessTokenLambdaResponse SUCCESS = new AddPersonalAccessTokenLambdaResponse(true, null);

    private final boolean success;
    private final String error;
}
