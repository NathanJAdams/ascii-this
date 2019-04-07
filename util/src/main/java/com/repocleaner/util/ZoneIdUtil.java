package com.repocleaner.util;

import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ZoneIdUtil {
    public static final Map<String, Set<String>> ZONE_ID_NAMES = new TreeMap<>();

    static {
        for (String name : ZoneOffset.getAvailableZoneIds()) {
            if (!name.startsWith("SystemV")) {
                String[] split = name.split("/");
                String main = split[0];
                Set<String> sub = ZONE_ID_NAMES.computeIfAbsent(main, k -> new TreeSet<>());
                sub.add(split[split.length - 1]);
            }
        }
    }
}
