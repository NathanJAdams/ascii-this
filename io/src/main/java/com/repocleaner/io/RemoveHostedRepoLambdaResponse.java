package com.repocleaner.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RemoveHostedRepoLambdaResponse {
    public static final RemoveHostedRepoLambdaResponse SUCCESS = new RemoveHostedRepoLambdaResponse(true, null);

    private final boolean success;
    private final String error;
}
