package clean.rest;

import clean.util.GsonUtil;
import clean.util.RepoCleanerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Rest {
    public <T> RestResponse<T> getResponse(RestRequest<T> request) throws RepoCleanerException {
        HttpURLConnection connection = createConnection(request.getUrl(), request.getHttpMethod().name());
        if (connection == null) {
            throw new RepoCleanerException("No connection available");
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

    private HttpURLConnection createConnection(String url, String method) throws RepoCleanerException {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod(method);
            return connection;
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to open url connection", e);
        }
    }

    private void setHeaders(HttpURLConnection connection, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private void sendBody(HttpURLConnection connection, Object object) throws RepoCleanerException {
        String json = GsonUtil.toJson(object);
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
            throw new RepoCleanerException("Failed to send connection body", e);
        }
    }

    private int getStatus(HttpURLConnection connection) throws RepoCleanerException {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to read response code", e);
        }
    }

    private <T> T getResponse(HttpURLConnection connection, Class<T> responseClass) throws RepoCleanerException {
        try (InputStream is = connection.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
            }
            String json = sb.toString();
            return GsonUtil.fromJsonOrNull(json, responseClass);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to read response from url", e);
        }
    }
}
