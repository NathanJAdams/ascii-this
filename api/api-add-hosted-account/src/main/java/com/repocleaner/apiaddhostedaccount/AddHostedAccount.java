package com.repocleaner.apiaddhostedaccount;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.cognito.CognitoValidator;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbSetter;
import com.repocleaner.model.user.HostedAccount;
import com.repocleaner.util.RepoCleanerException;

import java.util.Objects;

public class AddHostedAccount {
    public static boolean addHostedAccount(String jwt, HostedAccount hostedAccount) throws RepoCleanerException {
        String email = CognitoValidator.getValidEmail(jwt);
        if (!Objects.equals(email, hostedAccount.getHostedKey().getUserEmail())) {
            return false;
        }
        DatabaseReference hostAccountDbRef = DatabaseReferenceCreator.DB_CONNECTION
                .child("hostedAccounts");
        return new DbSetter<>(hostAccountDbRef, hostedAccount).set();
    }
}
