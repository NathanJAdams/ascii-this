package clean.user.config;

import clean.language.Language;

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
