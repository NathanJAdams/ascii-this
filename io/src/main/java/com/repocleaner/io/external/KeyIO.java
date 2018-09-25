package com.repocleaner.io.external;

import com.repocleaner.util.RepoCleanerException;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyIO {
    PublicKey getPublicKey(String id) throws RepoCleanerException;

    PrivateKey getPrivateKey(String id) throws RepoCleanerException;
}
