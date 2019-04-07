package com.repocleaner.apigateway.models;

import java.util.HashMap;
import java.util.Map;

public class JsonTypeNames {
    private static final Map<Class<?>, String> JSON_TYPE_NAMES = new HashMap<>();

    static {
        JSON_TYPE_NAMES.put(boolean.class, "boolean");

        JSON_TYPE_NAMES.put(byte.class, "integer");
        JSON_TYPE_NAMES.put(short.class, "integer");
        JSON_TYPE_NAMES.put(int.class, "integer");
        JSON_TYPE_NAMES.put(long.class, "integer");

        JSON_TYPE_NAMES.put(float.class, "number");
        JSON_TYPE_NAMES.put(double.class, "number");

        JSON_TYPE_NAMES.put(Object.class, "any");

        JSON_TYPE_NAMES.put(char.class, "string");
        JSON_TYPE_NAMES.put(String.class, "string");
    }

    public static boolean isPrimitiveJsonType(Class<?> refClass) {
        return (refClass.isPrimitive() || (refClass == Object.class) || (refClass == String.class));
    }

    public static String getTypeName(Class<?> type) {
        return JSON_TYPE_NAMES.get(type);
    }
}
