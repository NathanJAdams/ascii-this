package com.repocleaner.io.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EncryptRepoTokenLambdaResponse {
    private final boolean success;
    private final String encryptedToken;
    private final String error;
}
