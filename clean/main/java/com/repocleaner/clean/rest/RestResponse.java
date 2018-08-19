package com.repocleaner.clean.rest;

public class RestResponse<T> {
    private final int status;
    private final T response;

    public RestResponse(int status, T response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public T getResponse() {
        return response;
    }
}
