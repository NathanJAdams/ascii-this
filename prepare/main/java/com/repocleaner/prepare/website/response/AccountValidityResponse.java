package com.repocleaner.clean.website.response;

public class AccountValidityResponse {
    private final int responseCode;
    private final boolean is2xx;

    public AccountValidityResponse(int responseCode) {
        this.responseCode = responseCode;
        this.is2xx = (responseCode >= 200) && (responseCode < 300);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public boolean isIs2xx() {
        return is2xx;
    }
}
