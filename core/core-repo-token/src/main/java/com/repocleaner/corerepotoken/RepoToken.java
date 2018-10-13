package com.repocleaner.corerepotoken;

import com.repocleaner.secrets.SecretCommander;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.encrypt.AESEncryptor;
import com.repocleaner.util.encrypt.BytesEncoder;
import com.repocleaner.util.encrypt.Encryptor;

public class RepoToken {
    private static final Encryptor ENCRYPTOR;

    static {
        String secretKey = SecretCommander.getParameter(Constants.SECRET_ID_REPO_TOKEN_KEY);
        byte[] secretKeyContents = BytesEncoder.decode(secretKey);
        Encryptor encryptor = null;
        try {
            encryptor = new AESEncryptor(secretKeyContents);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        ENCRYPTOR = encryptor;
    }

    public String decryptToken(String encryptedToken) throws RepoCleanerException {
        return ENCRYPTOR.decrypt(encryptedToken);
    }

    public String encryptToken(String token) throws RepoCleanerException {
        return ENCRYPTOR.encrypt(token);
    }
}
