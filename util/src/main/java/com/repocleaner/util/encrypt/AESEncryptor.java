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
    private static final int SECRET_KEY_BITS = 256;
    private static final int SECRET_KEY_LENGTH = SECRET_KEY_BITS / 8;
    private static final int IV_LENGTH = 12;
    private static final int AUTHENTICATION_TAG_BITS = 128;
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    private final SecretKey secretKey;

    public AESEncryptor(byte[] secretKeyContents) throws RepoCleanerException {
        if (secretKeyContents.length != SECRET_KEY_LENGTH) {
            throw new RepoCleanerException("Key must be " + SECRET_KEY_LENGTH + " bytes long");
        }
        this.secretKey = new SecretKeySpec(secretKeyContents, "AES");
    }

    @Override
    public byte[] encrypt(byte[] data) throws RepoCleanerException {
        try {
            Cipher cipher = createCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] iv = cipher.getIV();
            if (iv.length != IV_LENGTH) {
                throw new RepoCleanerException("Failed to initialise encryption");
            }
            byte[] encrypted = cipher.doFinal(data);
            byte[] combined = new byte[IV_LENGTH + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, IV_LENGTH);
            System.arraycopy(encrypted, 0, combined, IV_LENGTH, encrypted.length);
            return combined;
        } catch (InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new RepoCleanerException("Failed to encrypt", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] encrypted) throws RepoCleanerException {
        if (encrypted.length < IV_LENGTH) {
            throw new RepoCleanerException("Encrypted data is too short");
        }
        try {
            Cipher cipher = createCipher();
            GCMParameterSpec params = new GCMParameterSpec(AUTHENTICATION_TAG_BITS, encrypted, 0, IV_LENGTH);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
            return cipher.doFinal(encrypted, IV_LENGTH, encrypted.length - IV_LENGTH);
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
