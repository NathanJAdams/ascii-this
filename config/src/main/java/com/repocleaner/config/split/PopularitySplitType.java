package com.repocleaner.config.split;

import com.repocleaner.model.config.SplitType;

public class PopularitySplitType implements SplitType {
    public String getType() {
        return "popularity";
    }
}
