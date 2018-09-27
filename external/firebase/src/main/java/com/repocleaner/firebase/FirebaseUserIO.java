package com.repocleaner.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.model.HostedRepo;
import com.repocleaner.model.User;
import com.repocleaner.util.LocalDateTimeUtil;
import com.repocleaner.util.RepoCleanerException;

import java.time.LocalDateTime;
import java.util.Map;

public class FirebaseUserIO implements UserIO {
    private final DatabaseReference databaseReference;

    public FirebaseUserIO(byte[] serviceAccountKeyContents) throws RepoCleanerException {
        this.databaseReference = new DatabaseReferenceCreator(serviceAccountKeyContents)
                .getDatabaseReference();
    }

    @Override
    public User getUser(String userId) {
        DatabaseReference userRef = databaseReference
                .child("users")
                .child(userId);
        return new DbGetter<>(userRef, User.class).get();
    }

    @Override
    public Map<String, HostedRepo> getHostedReposToClean(LocalDateTime end, int max) {
        String maxTimeHex = LocalDateTimeUtil.toHex(end);
        DatabaseReference reposRef = databaseReference
                .child("repos");
        Query query = reposRef
                .orderByChild("nextCleanTimeHex")
                .endAt(maxTimeHex)
                .limitToFirst(max);
        return new QueryGetter<>(query, HostedRepo.class)
                .get();
    }

    @Override
    public boolean setEncodedToken(String userId, String encodedToken) {
        DatabaseReference userTokenRef = databaseReference
                .child("users")
                .child(userId)
                .child("token");
        return new DbSetter<>(userTokenRef, encodedToken)
                .set();
    }
}
