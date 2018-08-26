package com.repocleaner.cognito;

import com.repocleaner.util.RepoCleanerException;

public class CognitoInfo {
    public static final String CLIENT_ID = "1f63er20154oqhllurcb4qs9cn";

    private static final String REGION = "eu-west-1";
    private static final String USER_POOL_ID = "eu-west-1_eU9ClkneX";
    private static final String PUBLIC_KEYS_ADDRESS = "https://cognito-idp." + REGION + ".amazonaws.com/" + USER_POOL_ID + "/.well-known/jwks.json";
    private static final PublicKeys PUBLIC_KEYS = new PublicKeysCommand(PUBLIC_KEYS_ADDRESS).execute();

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
