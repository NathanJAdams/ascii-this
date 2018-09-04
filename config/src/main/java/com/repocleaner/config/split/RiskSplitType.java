package com.repocleaner.config.split;

import com.repocleaner.model.config.SplitType;

public class RiskSplitType implements SplitType {
    public String getType() {
        return "risk";
    }
}
