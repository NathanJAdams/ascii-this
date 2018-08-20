package com.repocleaner.userinfo.config;

import com.repocleaner.userinfo.config.split.SplitType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserConfig {
    // TODO add branch name format
    private boolean isPaused;
    private int maxTokens;
    private List<SplitType> splitTypes;
    private TransformationPriority priority;
    private List<String> languages;
}
