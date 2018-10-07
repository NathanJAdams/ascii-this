package com.repocleaner.util.rest;

import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RestUtil {
    private static final JsonUtil JSON_UTIL = new JsonUtil();

    public static <T> RestResponse<T> getResponse(RestRequest<T> request) throws RepoCleanerException {
        String url = request.getUrl();
        String method = request.getHttpMethod().name();
        OpenConnectionCommand command = new OpenConnectionCommand(url, method);
        HttpURLConnection connection = command.open();
        if (connection == null) {
            return null;
        }
        setHeaders(connection, request.getHeaders());
        Object body = request.getBody();
        if (body != null) {
            sendBody(connection, body);
        }
        int status = getStatus(connection);
        T response = getResponse(connection, request.getResponseClass());
        return new RestResponse<>(status, response);
    }

    private static void setHeaders(HttpURLConnection connection, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private static void sendBody(HttpURLConnection connection, Object object) throws RepoCleanerException {
        String json = JSON_UTIL.toJson(object);
        if (json == null) {
            return;
        }
        try {
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to send http body", e);
        }
    }

    private static int getStatus(HttpURLConnection connection) throws RepoCleanerException {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to get status", e);
        }
    }

    private static <T> T getResponse(HttpURLConnection connection, Class<T> responseClass) throws RepoCleanerException {
        try (InputStream is = connection.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
            }
            String json = sb.toString();
            return JSON_UTIL.fromJsonOrNull(json, responseClass);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to get response", e);
        }
    }
}
