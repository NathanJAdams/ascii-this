package com.repocleaner.repotoken;

import com.repocleaner.io.external.KeyIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.util.KeyUtil;
import com.repocleaner.util.RepoCleanerException;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RepoToken {
    // TODO add public key and env var
    private static final String PUBLIC_KEY_ID = System.getenv("token_public_key");
    private static final String PRIVATE_KEY_ID = System.getenv("token_private_key");

    public static String decryptToken(KeyIO keyIO, String encryptedToken) throws RepoCleanerException {
        PrivateKey privateKey = keyIO.getPrivateKey(PRIVATE_KEY_ID);
        byte[] encryptedTokenBytes = Base64.getDecoder().decode(encryptedToken);
        byte[] tokenBytes = KeyUtil.decrypt(privateKey, encryptedTokenBytes);
        return new String(tokenBytes, StandardCharsets.UTF_8);
    }

    public static boolean encryptToken(KeyIO keyIO, UserIO userIO, String userId, String token) throws RepoCleanerException {
        PublicKey publicKey = keyIO.getPublicKey(PUBLIC_KEY_ID);
        byte[] tokenBytes = token.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedTokenBytes = KeyUtil.encrypt(publicKey, tokenBytes);
        String encryptedToken = Base64.getEncoder().encodeToString(encryptedTokenBytes);
        return userIO.setEncodedToken(userId, encryptedToken);
    }
}
