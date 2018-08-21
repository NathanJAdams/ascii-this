package com.repocleaner.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsonUtil {
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public static String toJson(Object object) {
        try {
            return GSON.toJson(object);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> T fromJsonOrNull(String json, Class<T> tClass) {
        try {
            return GSON.fromJson(json, tClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <T> void toJsonFile(T object, File file) throws RepoCleanerException {
        String json = JsonUtil.toJson(object);
        if (json == null) {
            throw new RepoCleanerException("Failed to create json");
        }
        try {
            IOUtils.saveToFile(json, file);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to save json", e);
        }
    }

    public static <T> T fromJsonFileOrNull(File file, Class<T> objectClass) throws RepoCleanerException {
        try {
            String json = IOUtils.toString(file, StandardCharsets.UTF_8);
            return fromJsonOrNull(json, objectClass);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to read json", e);
        }
    }
}
