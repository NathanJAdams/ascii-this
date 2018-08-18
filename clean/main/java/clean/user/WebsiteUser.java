package clean.user;

import clean.user.config.TransformationPriority;
import clean.user.config.UserConfig;
import clean.user.config.split.PopularitySplitType;

import java.util.ArrayList;

public class WebsiteUser implements User {
    private static final UserConfig USER_CONFIG = new UserConfig(new PopularitySplitType(), new TransformationPriority(null), new ArrayList<>());

    @Override
    public int countTokens() {
        return 1000000000;
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
        return USER_CONFIG;
    }
}
