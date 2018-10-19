package com.repocleaner.util.rest;

public class ResponseUtil {
    public static <T> T clientError(String reason) {
        return throwError("Required: ", reason);
    }

    public static <T> T internalError(String failure) {
        return throwError("Failure: ", failure);
    }

    private static <T> T throwError(String prefix, String error) {
        throw new RuntimeException(prefix + error);
    }
}
