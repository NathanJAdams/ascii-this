package com.repocleaner.util.rest;

import com.repocleaner.util.json.JsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RestUtil {
    private static final JsonUtil JSON_UTIL = new JsonUtil();

    public static <T> RestResponse<T> getResponse(RestRequest<T> request) {
        HttpURLConnection connection = createConnection(request.getUrl(), request.getHttpMethod().name());
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

    private static HttpURLConnection createConnection(String url, String method) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod(method);
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setHeaders(HttpURLConnection connection, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private static void sendBody(HttpURLConnection connection, Object object) {
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
            e.printStackTrace();
        }
    }

    private static int getStatus(HttpURLConnection connection) {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            return -1;
        }
    }

    private static <T> T getResponse(HttpURLConnection connection, Class<T> responseClass) {
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
            return null;
        }
    }
}
