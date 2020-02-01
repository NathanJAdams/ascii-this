package com.twitterbot.data.retrieve;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface ConnectionModifier {
    void modify(HttpURLConnection connection, String account) throws IOException;
}
