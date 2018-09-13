package com.repocleaner.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DatabaseReferenceCreator {
    public static final DatabaseReference DB_CONNECTION = createDatabaseConnection();

    static {
        if (DB_CONNECTION == null) {
            System.out.println("Cannot connect to database");
        }
    }

    private static DatabaseReference createDatabaseConnection() {
        String databaseUrl = System.getenv("database_url");
        String serviceAccountSecretName = System.getenv("service_account_key");
        String secret = SecretRetriever.getSecretAsString(serviceAccountSecretName);
        InputStream serviceAccount = toInputStream(secret);
        try {
//        try (FileInputStream serviceAccount = new FileInputStream("C:\\Users\\Nathan\\Desktop\\RepoCleaner\\repocleaner-db-service-account.json")) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setDatabaseUrl(databaseUrl)
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
