package com.repocleaner.util.encrypt;

import com.repocleaner.util.RepoCleanerException;

import java.nio.charset.StandardCharsets;

public interface Encryptor {
    default String encrypt(String dataString) throws RepoCleanerException {
        byte[] data = dataString.getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = encrypt(data);
        return BytesEncoder.encode(encrypted);
    }

    default String decrypt(String encryptedString) throws RepoCleanerException {
        byte[] decoded = BytesEncoder.decode(encryptedString);
        byte[] decrypted = decrypt(decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    byte[] encrypt(byte[] data) throws RepoCleanerException;

    byte[] decrypt(byte[] encrypted) throws RepoCleanerException;
}
