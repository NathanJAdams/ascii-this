package com.repocleaner.clean.user;

import com.repocleaner.clean.user.config.UserConfig;

public class ApiUser implements User {
    private final UserConfig userConfig;

    public ApiUser(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @Override
    public int countTokens() {
        return 1000;
    }

    @Override
    public void registerCleanEvent(int tokenCost) {

    }

    @Override
    public boolean canClean() {
        return true;
    }

    @Override
    public UserConfig getUserConfig() {
        return userConfig;
    }
}

