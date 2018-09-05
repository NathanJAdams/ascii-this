package com.repocleaner.apiremovehostedrepo;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.cognito.CognitoValidator;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbRemover;
import com.repocleaner.firebase.KeyConverter;
import com.repocleaner.util.RepoCleanerException;

public class RemoveHostedRepo {
    public static boolean removeHostedRepo(String jwt, String host, String account) throws RepoCleanerException {
        // TODO
//        String email = CognitoValidator.getValidEmail(jwt);
//        String emailKey = KeyConverter.toKey(email);
//        DatabaseReference hostAccountDbRef = DatabaseReferenceCreator.DB_CONNECTION
//                .child("hostedRepos")
//                .equalTo();
//        return new DbRemover<>(hostAccountDbRef).remove();
        return true;
    }
}
