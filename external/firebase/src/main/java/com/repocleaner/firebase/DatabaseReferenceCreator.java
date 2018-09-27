package com.repocleaner.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseReferenceCreator {
    @Getter
    private final DatabaseReference databaseReference;

    public DatabaseReferenceCreator(byte[] serviceAccountKeyContents) throws RepoCleanerException {
        this.databaseReference = createDatabaseConnection(serviceAccountKeyContents);
    }

    private static DatabaseReference createDatabaseConnection(byte[] serviceAccountKeyContents) throws RepoCleanerException {
        InputStream serviceAccount = new ByteArrayInputStream(serviceAccountKeyContents);
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setDatabaseUrl(Constants.FIREBASE_DATABASE_URL)
                    .build();
            FirebaseApp app = FirebaseApp.initializeApp(options);
            FirebaseDatabase database = FirebaseDatabase.getInstance(app);
            return database.getReference();
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to create db reference", e);
        }
    }
}
