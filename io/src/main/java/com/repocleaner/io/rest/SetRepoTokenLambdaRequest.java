package com.repocleaner.io.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SetRepoTokenLambdaRequest {
    private final String userId;
    private final String token;
}
