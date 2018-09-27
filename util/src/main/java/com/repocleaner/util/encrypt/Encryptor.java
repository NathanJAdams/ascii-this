package com.repocleaner.util.encrypt;

import com.repocleaner.util.RepoCleanerException;

public interface Encryptor {
    byte[] encrypt(byte[] data) throws RepoCleanerException;

    byte[] decrypt(byte[] encrypted) throws RepoCleanerException;
}
