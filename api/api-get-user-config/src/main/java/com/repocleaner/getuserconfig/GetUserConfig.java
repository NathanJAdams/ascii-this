package com.repocleaner.getuserconfig;

import com.repocleaner.cognito.CognitoValidator;
import com.repocleaner.config.Config;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbSelector;

public class GetUserConfig {
    public static Config getUserConfig(String jwt) {
        String email = CognitoValidator.getValidEmail(jwt);
        if (email == null) {
            return null;
        }
        return new DbSelector<>(DatabaseReferenceCreator.USERS_REF, Config.class, email, true).get();
    }
}
