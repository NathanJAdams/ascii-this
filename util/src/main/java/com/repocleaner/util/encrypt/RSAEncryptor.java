package com.repocleaner.util.encrypt;

import com.repocleaner.util.RepoCleanerException;

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

public class RSAEncryptor implements Encryptor {
    private static final String ALGORITHM = "RSA";
    private static final KeyFactory KEY_FACTORY;

    static {
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        KEY_FACTORY = keyFactory;
    }

    private final PublicKey publicKey;
    private final PrivateKey privateKey;

    private RSAEncryptor(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static RSAEncryptor getInstance(byte[] publicKeyContents, byte[] privateKeyContents) throws RepoCleanerException {
        PublicKey publicKey = createPublicKey(publicKeyContents);
        PrivateKey privateKey = createPrivateKey(privateKeyContents);
        return new RSAEncryptor(publicKey, privateKey);
    }

    public static RSAEncryptor getPublicKeyInstance(byte[] publicKeyContents) throws RepoCleanerException {
        PublicKey publicKey = createPublicKey(publicKeyContents);
        return new RSAEncryptor(publicKey, null);
    }

    public static RSAEncryptor getPrivateKeyInstance(byte[] privateKeyContents) throws RepoCleanerException {
        PrivateKey privateKey = createPrivateKey(privateKeyContents);
        return new RSAEncryptor(null, privateKey);
    }

    private static PublicKey createPublicKey(byte[] publicKeyContents) throws RepoCleanerException {
        try {
            return KEY_FACTORY.generatePublic(new X509EncodedKeySpec(publicKeyContents));
        } catch (InvalidKeySpecException e) {
            throw new RepoCleanerException("Failed to create public key", e);
        }
    }

    private static PrivateKey createPrivateKey(byte[] privateKeyContents) throws RepoCleanerException {
        try {
            return KEY_FACTORY.generatePrivate(new PKCS8EncodedKeySpec(privateKeyContents));
        } catch (InvalidKeySpecException e) {
            throw new RepoCleanerException("Failed to create private key", e);
        }
    }

    @Override
    public byte[] encrypt(byte[] data) throws RepoCleanerException {
        return en_de_crypt(Cipher.PUBLIC_KEY, publicKey, data);
    }

    @Override
    public byte[] decrypt(byte[] encrypted) throws RepoCleanerException {
        return en_de_crypt(Cipher.PRIVATE_KEY, privateKey, encrypted);
    }

    private byte[] en_de_crypt(int keyType, Key key, byte[] data) throws RepoCleanerException {
        if (key == null) {
            throw new RepoCleanerException("Cannot en/de crypt with null key");
        }
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
