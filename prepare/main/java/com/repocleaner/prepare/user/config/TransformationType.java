package com.repocleaner.clean.user.config;

import com.repocleaner.clean.language.Language;

public enum TransformationType {
    AddOverrideAnnotations(Language.Java);
    private final Language language;

    TransformationType(Language language) {
        this.language = language;
    }

    public Language getLanguage() {
        return language;
    }
}
