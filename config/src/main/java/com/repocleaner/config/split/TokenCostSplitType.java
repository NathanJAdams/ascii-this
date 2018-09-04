package com.repocleaner.config.split;

import com.repocleaner.model.config.SplitType;

public class TokenCostSplitType implements SplitType {
    public String getType() {
        return "token-cost";
    }
}
