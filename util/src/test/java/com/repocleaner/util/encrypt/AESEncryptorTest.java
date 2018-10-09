package com.repocleaner.util.encrypt;

import com.repocleaner.util.RepoCleanerException;
import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;

public class AESEncryptorTest {
    private SecureRandom secureRandom = new SecureRandom();

    @Test
    public void testSecretKey() throws RepoCleanerException {
        byte[] bytes = createBytes();
        AESEncryptor encryptor = new AESEncryptor(bytes);
        String data = "Hello";

        String encrypted1 = encryptor.encrypt(data);
        String encrypted2 = encryptor.encrypt(data);
        String decrypted1 = encryptor.decrypt(encrypted1);
        String decrypted2 = encryptor.decrypt(encrypted2);
        Assert.assertNotEquals(encrypted1, encrypted2);
        Assert.assertEquals(data, decrypted1);
        Assert.assertEquals(data, decrypted2);
    }

    private byte[] createBytes() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
}
