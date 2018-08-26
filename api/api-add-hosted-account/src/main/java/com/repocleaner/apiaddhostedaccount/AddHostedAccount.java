package com.repocleaner.apiaddhostedaccount;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.cognito.CognitoValidator;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbSetter;
import com.repocleaner.firebase.KeyCombiner;
import com.repocleaner.firebase.KeyConverter;
import com.repocleaner.user.HostedAccount;
import com.repocleaner.util.RepoCleanerException;

public class AddHostedAccount {
    public static boolean addHostedAccount(String jwt, String host, String account, HostedAccount hostedAccount) throws RepoCleanerException {
        String email = CognitoValidator.getValidEmail(jwt);
        String emailKey = KeyConverter.toKey(email);
        String hostAccountKey = KeyCombiner.combine(host, account);
        DatabaseReference hostAccountDbRef = DatabaseReferenceCreator.USERS_REF.child(emailKey).child("hostedAccounts").child(hostAccountKey);
        return new DbSetter<>(hostAccountDbRef, hostedAccount).set();
    }
}
