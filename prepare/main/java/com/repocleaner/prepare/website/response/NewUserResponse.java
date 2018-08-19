package com.repocleaner.clean.website.response;

public class NewUserResponse {
    private final boolean success;
    private final Integer id;

    public NewUserResponse(boolean success, Integer id) {
        this.success = success;
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getId() {
        return id;
    }
}
