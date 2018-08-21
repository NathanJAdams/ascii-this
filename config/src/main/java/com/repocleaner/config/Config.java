package com.repocleaner.config;

import com.repocleaner.config.transformation.TransformationPriority;
import com.repocleaner.config.split.SplitType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Config {
    private boolean isPaused;
    private int maxTokensPerClean;
    private List<SplitType> splitTypes;
    private TransformationPriority priority;
    private List<String> languages;
}
