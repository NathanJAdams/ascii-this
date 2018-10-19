package com.repocleaner.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.repocleaner.io.UserIO;
import com.repocleaner.model.HostedRepo;
import com.repocleaner.model.User;
import com.repocleaner.util.LocalDateTimeUtil;
import com.repocleaner.util.RepoCleanerException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

public class FirebaseUserIO implements UserIO {
    private final DatabaseReference databaseReference;

    public FirebaseUserIO(byte[] serviceAccountKeyContents) throws RepoCleanerException {
        this.databaseReference = FirebaseCommander.create(serviceAccountKeyContents);
    }

    public static FirebaseUserIO fromJson(String serviceAccountJson) throws RepoCleanerException {
        byte[] serviceAccountKeyContents = serviceAccountJson.getBytes(StandardCharsets.UTF_8);
        return new FirebaseUserIO(serviceAccountKeyContents);
    }

    @Override
    public User getUser(String userId) throws RepoCleanerException {
        DatabaseReference userRef = databaseReference
                .child("users")
                .child(userId);
        return FirebaseCommander.getValue(userRef, User.class);
    }

    @Override
    public Map<String, HostedRepo> getHostedReposToClean(LocalDateTime end, int max) throws RepoCleanerException {
        String maxTimeHex = LocalDateTimeUtil.toHex(end);
        DatabaseReference reposRef = databaseReference
                .child("repos");
        Query query = reposRef
                .orderByChild("nextCleanTimeHex")
                .endAt(maxTimeHex)
                .limitToFirst(max);
        return FirebaseCommander.query(query, HostedRepo.class);
    }
}
