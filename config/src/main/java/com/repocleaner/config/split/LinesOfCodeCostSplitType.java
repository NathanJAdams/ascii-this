package com.repocleaner.config.split;

import com.repocleaner.model.config.SplitType;

public class LinesOfCodeCostSplitType implements SplitType {
    public String getType() {
        return "lines-of-code";
    }
}
