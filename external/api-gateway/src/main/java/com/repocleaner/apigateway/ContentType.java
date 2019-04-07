package com.repocleaner.apigateway;

import lombok.Getter;

public enum ContentType {
    JSON("application/json");
    @Getter
    private final String description;

    ContentType(String description) {
        this.description = description;
    }
}
