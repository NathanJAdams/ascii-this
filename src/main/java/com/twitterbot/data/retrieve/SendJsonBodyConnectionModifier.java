package com.twitterbot.data.retrieve;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@AllArgsConstructor
public class SendJsonBodyConnectionModifier<T> implements ConnectionModifier {
    private static final Gson GSON = new Gson();

    private final Function<String, T> bodyCreator;

    @Override
    public void modify(HttpURLConnection connection, String account) throws IOException {
        T body = bodyCreator.apply(account);
        String bodyString = GSON.toJson(body);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = bodyString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }
}
