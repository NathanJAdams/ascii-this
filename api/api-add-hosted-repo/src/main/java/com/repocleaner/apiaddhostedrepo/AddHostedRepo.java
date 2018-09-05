package com.repocleaner.apiaddhostedrepo;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.cognito.CognitoValidator;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbSetter;
import com.repocleaner.model.user.HostedRepo;
import com.repocleaner.util.RepoCleanerException;

import java.util.Objects;

public class AddHostedRepo {
    public static boolean addHostedRepo(String jwt, HostedRepo hostedRepo) throws RepoCleanerException {
        String jwtEmail = CognitoValidator.getValidEmail(jwt);
        if (!Objects.equals(jwtEmail, hostedRepo.getUserEmail())) {
            return false;
        }
        DatabaseReference hostReposDbRef = DatabaseReferenceCreator.DB_CONNECTION
                .child("hostedRepos");
        return new DbSetter<>(hostReposDbRef,hostedRepo).set();
    }
}
