package com.repocleaner.apiaddpersonalaccesstoken;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.cognito.CognitoValidator;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbSetter;
import com.repocleaner.firebase.KeyCombiner;
import com.repocleaner.firebase.KeyConverter;
import com.repocleaner.util.RepoCleanerException;

public class AddPersonalAccessToken {
    public static boolean addPersonalAccessToken(String jwt, String host, String account, String personalAccessToken) throws RepoCleanerException {
        String email = CognitoValidator.getValidEmail(jwt);
        String emailKey = KeyConverter.toKey(email);
        String hostAccountKey = KeyCombiner.combine(host, account);
        DatabaseReference personalAccessTokenDbRef = DatabaseReferenceCreator.USERS_REF.child(emailKey).child("hostedAccounts").child(hostAccountKey).child(personalAccessToken);
        // TODO encrypt
        return new DbSetter<>(personalAccessTokenDbRef, true).set();
    }
}
