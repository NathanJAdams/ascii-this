package com.repocleaner.io.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddHostedRepoLambdaRequest {
    private final String jwt;
    private final String userEmail;
    private final String host;
    private final String userName;
    private final String repo;
    private final String masterBranch;
}
