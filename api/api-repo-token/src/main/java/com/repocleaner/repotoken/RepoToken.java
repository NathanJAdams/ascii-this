package com.repocleaner.repotoken;

import com.repocleaner.io.external.UserIO;
import com.repocleaner.secrets.SecretRetriever;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.encrypt.AESEncryptor;
import com.repocleaner.util.encrypt.Encryptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RepoToken {
    private static final Encryptor ENCRYPTOR;

    static {
        String secretKeyString = SecretRetriever.getSecretAsString(Constants.SECRET_ID_REPO_TOKEN_KEY);
        byte[] secretKeyContents = Base64.getDecoder().decode(secretKeyString);
        Encryptor encryptor = null;
        try {
            encryptor = new AESEncryptor(secretKeyContents);
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
