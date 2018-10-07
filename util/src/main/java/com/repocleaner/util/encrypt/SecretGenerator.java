package com.repocleaner.util.encrypt;

import java.security.SecureRandom;

public class SecretGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {
        String key = generateSymmetricKey(16);
        System.out.println(key);
    }

    public static String generateSymmetricKey(int length) {
        byte[] bytes = new byte[length];
        RANDOM.nextBytes(bytes);
        return BytesEncoder.encode(bytes);
    }
}
