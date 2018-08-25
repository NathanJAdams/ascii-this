package com.repocleaner.token;

import com.repocleaner.config.Config;

public interface TokenRestriction {
    boolean isValid(Config config, int creditCost);
}
