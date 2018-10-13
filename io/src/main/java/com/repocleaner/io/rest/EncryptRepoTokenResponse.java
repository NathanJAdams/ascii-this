package com.repocleaner.io.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EncryptRepoTokenResponse {
    private final ResponseInfo responseInfo;
    private final String encryptedToken;
}
