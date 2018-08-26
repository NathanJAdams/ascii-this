package com.repocleaner.apiremovehostedaccount;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.cognito.CognitoValidator;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbRemover;
import com.repocleaner.firebase.KeyCombiner;
import com.repocleaner.firebase.KeyConverter;
import com.repocleaner.util.RepoCleanerException;

public class RemoveHostedAccount {
    public static boolean removeHostedAccount(String jwt, String host, String account) throws RepoCleanerException {
        String email = CognitoValidator.getValidEmail(jwt);
        String emailKey = KeyConverter.toKey(email);
        String hostAccountKey = KeyCombiner.combine(host, account);
        DatabaseReference hostAccountDbRef = DatabaseReferenceCreator.USERS_REF.child(emailKey).child("hostedAccounts").child(hostAccountKey);
        return new DbRemover<>(hostAccountDbRef).remove();
    }
}