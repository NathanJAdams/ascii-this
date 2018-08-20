package com.repocleaner.userinfo.config;

import lombok.Getter;

@Getter
public enum TransformationType {
    AddOverrideAnnotations("Java");
    private final String language;

    TransformationType(String language) {
        this.language = language;
    }
}
