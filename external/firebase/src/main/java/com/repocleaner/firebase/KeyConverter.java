package com.repocleaner.firebase;

public class KeyConverter {
    private static final char[] RAW = {'.', '$', '#', '[', ']', '/', '_'};
    private static final char[] ENCODED = {'P', 'D', 'H', 'O', 'C', 'S', 'U'};

    public static String toKey(String raw) {
        StringBuilder sb = new StringBuilder();
        for (char rawChar : raw.toCharArray()) {
            char encoded = getEncoded(rawChar);
            if (encoded != rawChar) {
                sb.append('_');
            }
            sb.append(encoded);
        }
        return sb.toString();
    }

    public static String toRaw(String key) {
        int lastIndex = key.length() - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lastIndex; i++) {
            char encodedChar = key.charAt(i);
            char rawChar = encodedChar;
            if (encodedChar == '_') {
                i++;
                encodedChar = key.charAt(i);
                rawChar = getRaw(encodedChar);
            }
            sb.append(rawChar);
        }
        char lastChar = key.charAt(lastIndex);
        // ignore bad encoding
        sb.append(lastChar);
        return sb.toString();
    }

    private static char getRaw(char encodedChar) {
        return getOther(ENCODED, encodedChar, RAW);
    }

    private static char getEncoded(char rawChar) {
        return getOther(RAW, rawChar, ENCODED);
    }

    private static char getOther(char[] original, char originalChar, char[] other) {
        for (int i = 0; i < original.length; i++) {
            if (original[i] == originalChar) {
                return other[i];
            }
        }
        return originalChar;
    }
}
