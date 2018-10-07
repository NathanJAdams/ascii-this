package com.repocleaner.util.encrypt;

import java.util.Base64;

public class BytesEncoder {
    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] decode(String string) {
        return Base64.getDecoder().decode(string);
    }
}
