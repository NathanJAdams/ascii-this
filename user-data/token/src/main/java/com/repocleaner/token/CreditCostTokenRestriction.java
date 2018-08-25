package com.repocleaner.token;

import com.repocleaner.config.Config;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreditCostTokenRestriction implements TokenRestriction {
    private final int maxCreditCost;

    @Override
    public boolean isValid(Config config, int creditCost) {
        return (creditCost <= maxCreditCost);
    }
}
