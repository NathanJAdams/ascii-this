package com.repocleaner.firebase;

import java.util.regex.Pattern;

public class KeyCombiner {
    private static final Pattern SPLIT_PATTERN = Pattern.compile("__");

    public static String combine(String... elements) {
        StringBuilder sb = new StringBuilder();
        for (String element : elements) {
            sb.append(element);
            sb.append("__");
        }
        if (elements.length > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public static String[] extract(String key) {
        return SPLIT_PATTERN.split(key);
    }
}
