package com.twitterbot.data.retrieve;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import java.util.function.ToIntFunction;

@AllArgsConstructor
public class JsonCountExtractor<T> implements CountExtractor {
    private static final Gson GSON = new Gson();

    private final Class<T> extractedClass;
    private final ToIntFunction<T> extractorFunction;

    @Override
    public int extract(String content) {
        T extracted = GSON.fromJson(content, extractedClass);
        return extractorFunction.applyAsInt(extracted);
    }
}
