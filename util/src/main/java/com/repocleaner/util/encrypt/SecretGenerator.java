package com.repocleaner.util.encrypt;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {
        byte[]key = generateSymmetricKey(32);
        System.out.println(Base64.getEncoder().encodeToString(key));
    }
    public static byte[] generateSymmetricKey(int length) {
        byte[] bytes = new byte[length];
        RANDOM.nextBytes(bytes);
        return bytes;
    }
}
