package com.repocleaner.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.model.HostedRepo;
import com.repocleaner.model.User;
import com.repocleaner.util.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.util.Map;

public class FirebaseUserIO implements UserIO {
    private final DatabaseReference databaseReference;

    public FirebaseUserIO(byte[] serviceAccountKeyContents) {
        this.databaseReference = FirebaseCommander.create(serviceAccountKeyContents);
    }

    @Override
    public User getUser(String userId) {
        DatabaseReference userRef = databaseReference
                .child("users")
                .child(userId);
        return FirebaseCommander.getValue(userRef, User.class);
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
        return FirebaseCommander.query(query, HostedRepo.class);
    }

    @Override
    public boolean setEncodedToken(String userId, String encodedToken) {
        DatabaseReference userTokenRef = databaseReference
                .child("users")
                .child(userId)
                .child("token");
        return FirebaseCommander.set(userTokenRef, encodedToken);
    }
}
