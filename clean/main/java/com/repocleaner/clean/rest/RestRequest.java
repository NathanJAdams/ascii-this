package com.repocleaner.clean.rest;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RestRequest<T> {
    public enum HttpMethod {
        GET,
        POST,
    }

    private final String url;
    private final Map<String, String> headers = new HashMap<>();
    private final HttpMethod httpMethod;
    private final Object body;
    private final Class<T> responseClass;

    public static <T> RestRequest<T> GET(String url, Class<T> responseClass) {
        return new RestRequest<>(url, HttpMethod.GET, null, responseClass);
    }

    public static <T> RestRequest<T> POST(String url, Object body, Class<T> responseClass) {
        return new RestRequest<>(url, HttpMethod.POST, body, responseClass);
    }

    private RestRequest(String url, HttpMethod httpMethod, Object body, Class<T> responseClass) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.body = body;
        this.responseClass = responseClass;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Object getBody() {
        return body;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

    public RestRequest<T> withBasicAuthFromEnvironmentVars() {
        String username = System.getenv("username");
        String password = System.getenv("password");
        return withBasicAuth(username, password);
    }

    public RestRequest<T> withBasicAuth(String username, String password) {
        String auth = username + ':' + password;
        byte[] authBytes = auth.getBytes(StandardCharsets.UTF_8);
        byte[] encodedAuth = Base64.encodeBase64(authBytes);
        String authHeader = "Basic " + new String(encodedAuth, StandardCharsets.UTF_8);
        headers.put("Authorization", authHeader);
        return this;
    }

    public RestRequest<T> withHeader(String header, String value) {
        headers.put(header, value);
        return this;
    }
}
