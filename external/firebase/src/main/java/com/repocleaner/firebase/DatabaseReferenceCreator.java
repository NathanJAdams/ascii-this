package com.repocleaner.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DatabaseReferenceCreator {
    private static final String DATABASE_URL = System.getenv("db_url");
    private static final String SERVICE_ACCOUNT_SECRET_NAME = System.getenv("service_account_key");
    private static final DatabaseReference DB_CONNECTION = createDatabaseConnection();
    public static final DatabaseReference USERS_REF = (DB_CONNECTION == null)
            ? null
            : DB_CONNECTION.child("users");

    public static DatabaseReference getDbConnection() {
        return DB_CONNECTION;
    }

    private static DatabaseReference createDatabaseConnection() {
        String secret = SecretRetriever.getSecretAsString(SERVICE_ACCOUNT_SECRET_NAME);
        if (secret == null) {
            System.out.println("Null service account");
            return null;
        }
        InputStream serviceAccount = toInputStream(secret);
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp app = FirebaseApp.initializeApp(options);
            FirebaseDatabase database = FirebaseDatabase.getInstance(app);
            return database.getReference();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static InputStream toInputStream(String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        return new ByteArrayInputStream(bytes);
    }
}
