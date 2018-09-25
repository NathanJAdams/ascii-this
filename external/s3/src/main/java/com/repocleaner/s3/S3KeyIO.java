package com.repocleaner.s3;

import com.repocleaner.io.external.KeyIO;
import com.repocleaner.util.KeyUtil;
import com.repocleaner.util.RepoCleanerException;

import java.security.PrivateKey;
import java.security.PublicKey;

public class S3KeyIO implements KeyIO {
    @Override
    public PublicKey getPublicKey(String id) throws RepoCleanerException {
        // TODO
        byte[] publicKeyContents = {};
        return KeyUtil.createPublicKey(publicKeyContents);
    }

    @Override
    public PrivateKey getPrivateKey(String id) throws RepoCleanerException {
        // TODO
        byte[] privateKeyContents = {};
        return KeyUtil.createPrivateKey(privateKeyContents);
    }
}
