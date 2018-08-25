package com.repocleaner.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class JsonUtil {
    private final Gson gson;

    public JsonUtil(GsonCustomiser... customisers) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Arrays.stream(customisers).forEach(customiser -> customiser.customise(builder));
        this.gson = builder.create();
    }

    public String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public <T> T fromJsonOrNull(String json, Class<T> tClass) {
        try {
            return gson.fromJson(json, tClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public <T> void toJsonFile(T object, File file) throws RepoCleanerException {
        String json = toJson(object);
        if (json == null) {
            throw new RepoCleanerException("Failed to create json");
        }
        try {
            IOUtils.saveToFile(json, file);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to save json", e);
        }
    }

    public <T> T fromJsonFileOrNull(File file, Class<T> objectClass) throws RepoCleanerException {
        try {
            String json = IOUtils.toString(file, StandardCharsets.UTF_8);
            return fromJsonOrNull(json, objectClass);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to read json", e);
        }
    }
}
