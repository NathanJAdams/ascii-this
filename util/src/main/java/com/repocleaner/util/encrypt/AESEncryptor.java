package com.repocleaner.util.encrypt;

import com.repocleaner.util.RepoCleanerException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESEncryptor implements Encryptor {
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    private final SecretKey secretKey;

    public AESEncryptor(byte[] secretKeyContents) throws RepoCleanerException {
        if (secretKeyContents.length != 32) {
            throw new RepoCleanerException("Key is not 32 bytes long");
        }
        this.secretKey = new SecretKeySpec(secretKeyContents, "AES");
    }

    @Override
    public byte[] encrypt(byte[] data) throws RepoCleanerException {
        try {
            Cipher cipher = createCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();
            byte[] encrypted = cipher.doFinal(data);
            byte[] combined = new byte[12 + data.length + 16];
            System.arraycopy(iv, 0, combined, 0, 12);
            System.arraycopy(encrypted, 0, combined, 12, encrypted.length);
            return combined;
        } catch (InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new RepoCleanerException("Failed to encrypt", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] encrypted) throws RepoCleanerException {
        if (encrypted.length < 12 + 16) {
            throw new RepoCleanerException("Encrypted data is too short");
        }
        try {
            Cipher cipher = createCipher();
            GCMParameterSpec params = new GCMParameterSpec(128, encrypted, 0, 12);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
            return cipher.doFinal(encrypted, 12, encrypted.length - 12);
        } catch (InvalidKeyException
                | InvalidAlgorithmParameterException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new RepoCleanerException("Failed to decrypt", e);
        }
    }

    private Cipher createCipher() throws RepoCleanerException {
        try {
            return Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException e) {
            throw new RepoCleanerException("Failed to create Cipher", e);
        }
    }
}
