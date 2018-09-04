package com.repocleaner.config.split;

import com.repocleaner.model.config.SplitType;

public class TransformationSplitType implements SplitType {
    public String getType() {
        return "transformation";
    }
}
