package users.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.apache.commons.io.input.ReaderInputStream;
import users.security.SecretRetriever;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

public class DatabaseReferenceCreator {
    private static final String DATABASE_URL = System.getenv("db_url");
    private static final DatabaseReference DB_CONNECTION = createDatabaseConnection();

    public static DatabaseReference getDbConnection() {
        return DB_CONNECTION;
    }

    private static DatabaseReference createDatabaseConnection() {
        InputStream serviceAccount = createServiceAccountInputStream();
        if (serviceAccount == null) {
            System.out.println("Null service account");
            return null;
        }
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setDatabaseUrl(DATABASE_URL)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return FirebaseDatabase.getInstance().getReference();
    }

    private static InputStream createServiceAccountInputStream() {
        String secret = SecretRetriever.getSecretAsString("prod/firebase/serviceaccount");
        if (secret == null) {
            return null;
        }
        StringReader sr = new StringReader(secret);
        return new ReaderInputStream(sr, StandardCharsets.UTF_8);
    }
}
