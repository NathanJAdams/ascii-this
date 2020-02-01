package com.twitterbot.data.retrieve;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class ListConnectionModifier implements ConnectionModifier {
    private final List<ConnectionModifier> list;

    public ListConnectionModifier(ConnectionModifier... connectionModifiers) {
        this(Arrays.asList(connectionModifiers));
    }

    @Override
    public void modify(HttpURLConnection connection, String account) throws IOException {
        for (ConnectionModifier modifier : list) {
            modifier.modify(connection, account);
        }
    }
}
