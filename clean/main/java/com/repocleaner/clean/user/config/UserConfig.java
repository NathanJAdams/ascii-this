package com.repocleaner.clean.user.config;

import com.repocleaner.clean.language.Language;
import com.repocleaner.clean.user.config.split.SplitType;

import java.util.List;

public class UserConfig {
    // TODO add branch name format
    private final SplitType splitType;
    private final TransformationPriority priority;
    private final List<Language> languages;

    public UserConfig(SplitType splitType, TransformationPriority priority, List<Language> languages) {
        this.splitType = splitType;
        this.priority = priority;
        this.languages = languages;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public TransformationPriority getPriority() {
        return priority;
    }

    public List<Language> getLanguages() {
        return languages;
    }
}
