package com.repocleaner.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyUtil {
    private static final String ALGORITHM = "RSA";
    private static final KeyFactory KEY_FACTORY = createKeyFactory();

    private static KeyFactory createKeyFactory() {
        try {
            return KeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey createPublicKey(byte[] publicKeyContents) throws RepoCleanerException {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyContents);
            return KEY_FACTORY.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RepoCleanerException("Failed to create public key", e);
        }
    }

    public static PrivateKey createPrivateKey(byte[] privateKeyContents) throws RepoCleanerException {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyContents);
            return KEY_FACTORY.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new RepoCleanerException("Failed to create private key", e);
        }
    }

    public static byte[] encrypt(PublicKey publicKey, byte[] data) throws RepoCleanerException {
        return en_de_crypt(Cipher.PUBLIC_KEY, publicKey, data);
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws RepoCleanerException {
        return en_de_crypt(Cipher.PRIVATE_KEY, privateKey, data);
    }

    private static byte[] en_de_crypt(int keyType, Key key, byte[] data) throws RepoCleanerException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(keyType, key);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new RepoCleanerException("Failed to en/de-crypt data", e);
        }
    }
}
