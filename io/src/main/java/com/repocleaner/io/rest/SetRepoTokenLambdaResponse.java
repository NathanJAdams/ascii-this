package com.repocleaner.io.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SetRepoTokenLambdaResponse {
    public static final SetRepoTokenLambdaResponse SUCCESS = new SetRepoTokenLambdaResponse(true, null);

    private final boolean success;
    private final String error;
}
