package com.repocleaner.util;

public class StringUtil {
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isEmpty(String string) {
        return (string == null) || (string.length() == 0);
    }
}
