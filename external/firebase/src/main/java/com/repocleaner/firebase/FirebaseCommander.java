package com.repocleaner.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.repocleaner.util.HystrixCommander;
import com.repocleaner.util.Constants;

import java.util.Map;

public class FirebaseCommander {
    public static DatabaseReference create(byte[] serviceAccountKeyContents) {
        FirebaseCreateDatabaseReferenceCommand command = new FirebaseCreateDatabaseReferenceCommand(serviceAccountKeyContents);
        return HystrixCommander.run(Constants.FIREBASE_DATABASE_URL, command::createDatabaseConnection);
    }

    public static <T> T getValue(DatabaseReference databaseReference, Class<T> valueClass) {
        FirebaseGetCommand<T> command = new FirebaseGetCommand<>(databaseReference, valueClass);
        return HystrixCommander.run(Constants.FIREBASE_DATABASE_URL, command::getValue);
    }

    public static <T> Map<String, T> query(Query query, Class<T> valueClass) {
        FirebaseQueryCommand<T> command = new FirebaseQueryCommand<>(query, valueClass);
        return HystrixCommander.run(Constants.FIREBASE_DATABASE_URL, command::get);
    }

    public static boolean remove(DatabaseReference databaseReference) {
        FirebaseRemoveCommand<Boolean> command = new FirebaseRemoveCommand<>(databaseReference);
        return HystrixCommander.run(Constants.FIREBASE_DATABASE_URL, command::remove);
    }

    public static <T> boolean set(DatabaseReference databaseReference, T value) {
        FirebaseSetCommand<T> command = new FirebaseSetCommand<>(databaseReference, value);
        return HystrixCommander.run(Constants.FIREBASE_DATABASE_URL, command::set);
    }
}
