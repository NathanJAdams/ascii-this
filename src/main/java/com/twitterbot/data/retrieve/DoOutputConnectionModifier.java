package com.twitterbot.data.retrieve;

import java.io.IOException;
import java.net.HttpURLConnection;

public class DoOutputConnectionModifier implements ConnectionModifier {
    @Override
    public void modify(HttpURLConnection connection, String account) throws IOException {
        connection.setDoOutput(true);
    }
}