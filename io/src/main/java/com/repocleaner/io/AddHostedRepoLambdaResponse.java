package com.repocleaner.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddHostedRepoLambdaResponse {
    public static final AddHostedRepoLambdaResponse SUCCESS = new AddHostedRepoLambdaResponse(true, null);

    private final boolean success;
    private final String error;
}
