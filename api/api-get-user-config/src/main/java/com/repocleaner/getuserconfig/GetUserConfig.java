package com.repocleaner.getuserconfig;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.cognito.CognitoValidator;
import com.repocleaner.config.Config;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbGetter;
import com.repocleaner.firebase.KeyConverter;
import com.repocleaner.util.RepoCleanerException;

public class GetUserConfig {
    public static Config getUserConfig(String jwt) throws RepoCleanerException {
        String email = CognitoValidator.getValidEmail(jwt);
        String emailKey = KeyConverter.toKey(email);
        DatabaseReference userDbRef = DatabaseReferenceCreator.USERS_REF.child(emailKey);
        return new DbGetter<>(userDbRef, Config.class).get();
    }
}
