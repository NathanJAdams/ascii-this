package com.repocleaner.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GsonUtil {
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public static <T> T fromJsonOrNull(String json, Class<T> tClass) {
        try {
            return GSON.fromJson(json, tClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static String toJson(Object object) {
        try {
            return GSON.toJson(object);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
}
