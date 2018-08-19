package users.db;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class DatabaseReferenceCreator {
    private static final String DATABASE_URL = System.getenv("db_url");
    private static final DatabaseReference DB_CONNECTION = createDatabaseConnection();

    public static DatabaseReference getDbConnection() {
        return DB_CONNECTION;
    }

    private static DatabaseReference createDatabaseConnection() {
        StringBuilder sb = createServiceAccountStringBuilder();
        InputStream serviceAccount = createServiceAccountInputStream(sb);
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

    private static InputStream createServiceAccountInputStream(StringBuilder sb) {
        char[] chars = new char[sb.length()];
        sb.getChars(0, sb.length(), chars, 0);
        Reader reader = new CharArrayReader(chars);
        return new ReaderInputStream(reader, StandardCharsets.UTF_8);
    }

    private static StringBuilder createServiceAccountStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        appendKeyValue(sb, "type", "service_account");
        appendKeyEnvVar(sb, "project_id");
        appendKeyEnvVar(sb, "private_key_id");
        appendKeyEnvVar(sb, "private_key");
        appendKeyEnvVar(sb, "client_email");
        appendKeyEnvVar(sb, "client_id");
        appendKeyEnvVar(sb, "auth_uri");
        appendKeyEnvVar(sb, "token_uri");
        appendKeyEnvVar(sb, "auth_provider_x509_cert_url");
        appendKeyEnvVar(sb, "client_x509_cert_url");
        sb.setLength(sb.length() - 1);
        sb.append("}");
        return sb;
    }

    private static void appendKeyEnvVar(StringBuilder sb, String key) {
        appendKeyEnvVar(sb, key, key);
    }

    private static void appendKeyEnvVar(StringBuilder sb, String key, String envVarKey) {
        String envVar = System.getenv(envVarKey);
        appendKeyValue(sb, key, envVar);
    }

    private static void appendKeyValue(StringBuilder sb, String key, String value) {
        sb.append('"');
        sb.append(key);
        sb.append('"');
        sb.append(':');
        sb.append('"');
        sb.append(value);
        sb.append('"');
        sb.append(',');
    }
}
