package com.repocleaner.repotoken;

import com.repocleaner.io.external.UserIO;
import com.repocleaner.secrets.SecretRetriever;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.encrypt.Encryptor;
import com.repocleaner.util.encrypt.RSAEncryptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RepoToken {
    private static final Encryptor ENCRYPTOR;

    static {
        String privateKeySecret = SecretRetriever.getSecretAsString(Constants.SECRET_ID_REPO_TOKEN_PRIVATE_KEY);
        String publicKeySecret = SecretRetriever.getSecretAsString(Constants.SECRET_ID_REPO_TOKEN_PUBLIC_KEY);
        byte[] privateKeyContents = Base64.getDecoder().decode(privateKeySecret);
        byte[] publicKeyContents = Base64.getDecoder().decode(publicKeySecret);
        Encryptor encryptor = null;
        try {
            encryptor = RSAEncryptor.getInstance(publicKeyContents, privateKeyContents);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        ENCRYPTOR = encryptor;
    }

    public String decryptToken(String encryptedToken) throws RepoCleanerException {
        byte[] encryptedTokenBytes = Base64.getDecoder().decode(encryptedToken);
        byte[] decryptedTokenBytes = ENCRYPTOR.decrypt(encryptedTokenBytes);
        return new String(decryptedTokenBytes, StandardCharsets.UTF_8);
    }

    public boolean encryptToken(UserIO userIO, String userId, String token) throws RepoCleanerException {
        byte[] tokenBytes = token.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedTokenBytes = ENCRYPTOR.encrypt(tokenBytes);
        String encryptedToken = Base64.getEncoder().encodeToString(encryptedTokenBytes);
        return userIO.setEncodedToken(userId, encryptedToken);
    }
}
