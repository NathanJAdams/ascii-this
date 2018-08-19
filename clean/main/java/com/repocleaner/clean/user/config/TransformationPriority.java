package com.repocleaner.clean.user.config;

import com.repocleaner.clean.language.Language;

import java.util.List;
import java.util.Map;

public class TransformationPriority {
    private final Map<Language, List<TransformationType>> priorities;

    public TransformationPriority(Map<Language, List<TransformationType>> priorities) {
        this.priorities = priorities;
    }

    public Map<Language, List<TransformationType>> getPriorities() {
        return priorities;
    }
}
