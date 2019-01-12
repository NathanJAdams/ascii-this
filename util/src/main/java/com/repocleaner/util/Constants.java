package com.repocleaner.util;

public class Constants {
    public static final String AWS_REGION = "eu-west-1";

    public static final String SECRET_ID_SERVICE_ACCOUNT_KEY = "serviceAccountKey";
    public static final String SECRET_ID_REPO_TOKEN_KEY = "repoTokenKey";

    public static final String FIREBASE_DATABASE_URL = "https://repocleaner-db.firebaseio.com";

    private static final String BUCKET_SUFFIX = ".repocleaner.com";
    public static final String BUCKET_REQUESTED = "requested" + BUCKET_SUFFIX;
    public static final String BUCKET_DOWNLOADED = "downloaded" + BUCKET_SUFFIX;
    public static final String BUCKET_CLEANED = "cleaned" + BUCKET_SUFFIX;
    public static final String BUCKET_WAITING = "waiting" + BUCKET_SUFFIX;

    public static final String DIFF_FOLDER = "diff";
    public static final String DIFF_FILE = "diff.txt";
}
