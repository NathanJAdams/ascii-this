package com.twitterbot.data.retrieve;

import java.io.IOException;
import java.net.HttpURLConnection;

public class JsonContentConnectionModifier implements ConnectionModifier {
    @Override
    public void modify(HttpURLConnection connection, String account) throws IOException {
        connection.addRequestProperty("Content-Type", "application/json");
    }
}
