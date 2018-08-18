package clean.user;

import clean.user.config.UserConfig;

public interface User {
    int countTokens();

    void registerCleanEvent(int tokenCost);

    boolean canClean();

    UserConfig getUserConfig();
}
