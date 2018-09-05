package com.repocleaner.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RemoveHostedRepoLambdaRequest {
    private final String jwt;
    private final String host;
    private final String account;
}
