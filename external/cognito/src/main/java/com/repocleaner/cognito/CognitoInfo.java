package com.repocleaner.cognito;

import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;

public class CognitoInfo {
    private static final PublicKeys PUBLIC_KEYS = new PublicKeysCommand(Constants.COGNITO_PUBLIC_KEYS_ADDRESS).execute();

    public static PublicKey getKey(String keyId) throws RepoCleanerException {
        if ((PUBLIC_KEYS == null) || !PUBLIC_KEYS.hasKeys()) {
            throw new RepoCleanerException("No public key available");
        }
        PublicKey publicKey = PUBLIC_KEYS.getKey(keyId);
        if (publicKey == null) {
            throw new RepoCleanerException("No public key available");
        }
        return publicKey;
    }
}
