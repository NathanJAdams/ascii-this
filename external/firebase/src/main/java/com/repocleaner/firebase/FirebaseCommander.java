package com.repocleaner.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.repocleaner.util.RepoCleanerException;

import java.util.Map;

public class FirebaseCommander {
    public static DatabaseReference create(byte[] serviceAccountKeyContents) throws RepoCleanerException {
        return new FirebaseCreateDatabaseReferenceCommand(serviceAccountKeyContents).createDatabaseConnection();
    }

    public static <T> T getValue(DatabaseReference databaseReference, Class<T> valueClass) throws RepoCleanerException {
        return new FirebaseGetCommand<>(databaseReference, valueClass).getValue();
    }

    public static <T> Map<String, T> query(Query query, Class<T> valueClass) throws RepoCleanerException {
        return new FirebaseQueryCommand<>(query, valueClass).get();
    }

    public static boolean remove(DatabaseReference databaseReference) throws RepoCleanerException {
        return new FirebaseRemoveCommand<>(databaseReference).remove();
    }

    public static <T> boolean set(DatabaseReference databaseReference, T value) throws RepoCleanerException {
        return new FirebaseSetCommand<>(databaseReference, value).set();
    }
}
