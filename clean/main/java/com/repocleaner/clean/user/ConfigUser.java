package com.repocleaner.clean.user;

import com.repocleaner.clean.user.config.UserConfig;

public class ConfigUser implements User {
    private final UserConfig userConfig;

    public ConfigUser(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @Override
    public int countTokens() {
        return 0;
    }

    @Override
    public void registerCleanEvent(int tokenCost) {
    }

    @Override
    public boolean canClean() {
        // TODO return NOT (does the repo-cleaner branch exist and is it mergeable and has not been committed)
        return false;
    }

    @Override
    public UserConfig getUserConfig() {
        return userConfig;
    }
}

