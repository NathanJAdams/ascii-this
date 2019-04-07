package com.repocleaner.lambdaencryptrepotoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EncryptRepoTokenResponse {
    private final String encryptedToken;
}
