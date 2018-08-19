package com.repocleaner.clean.website.response;

public class NewAccessTokenResponse {
    private final boolean success;

    public NewAccessTokenResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
