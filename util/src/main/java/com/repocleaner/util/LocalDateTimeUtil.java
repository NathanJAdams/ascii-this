package com.repocleaner.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeUtil {
    public static LocalDateTime toLocalDateTime(String hex) {
        long epochSecond = Long.parseLong(hex, 16);
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC);
    }

    public static String toHex(LocalDateTime localDateTime) {
        long epochSecond = localDateTime.toEpochSecond(ZoneOffset.UTC);
        return String.format("%016x", epochSecond);
    }
}
