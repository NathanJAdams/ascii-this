package com.repocleaner.util.rest;

import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@AllArgsConstructor
public class OpenConnectionCommand {
    private final String url;
    private final String method;

    public HttpURLConnection open() throws RepoCleanerException {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod(method);
            return connection;
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to open connection", e);
        }
    }
}
