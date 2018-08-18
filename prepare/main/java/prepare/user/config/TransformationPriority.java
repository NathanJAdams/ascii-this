package clean.user.config;

import clean.language.Language;

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
