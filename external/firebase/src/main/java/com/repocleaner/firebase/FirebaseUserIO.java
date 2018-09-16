package com.repocleaner.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.model.user.HostedRepo;
import com.repocleaner.model.user.User;
import com.repocleaner.util.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.util.Map;

public class FirebaseUserIO implements UserIO {
    @Override
    public User getUser(String userId) {
        DatabaseReference userRef = DatabaseReferenceCreator.DB_CONNECTION
                .child("users")
                .child(userId);
        return new DbGetter<>(userRef, User.class).get();
    }

    @Override
    public Map<String, HostedRepo> getHostedReposToClean(LocalDateTime end, int max) {
        String maxTimeHex = LocalDateTimeUtil.toHex(end);
        DatabaseReference reposRef = DatabaseReferenceCreator.DB_CONNECTION
                .child("repos");
        Query query = reposRef.orderByChild("nextCleanTimeHex")
                .endAt(maxTimeHex)
                .limitToFirst(max);
        return new QueryGetter<>(query, HostedRepo.class).get();
    }
}
