package com.repocleaner.util;

public class Constants {
    public static final String AWS_REGION = "eu-west-1";

    public static final String LAMBDA_PREPARE_NAME = "prepare";

    public static final String BUCKET_WAITING = "waiting.repocleaner.com";
    public static final String BUCKET_PREPARED = "prepared.repocleaner.com";
    public static final String BUCKET_CLEANED = "cleaned.repocleaner.com";
    public static final String BUCKET_HELD = "held.repocleaner.com";

    public static final String COGNITO_CLIENT_ID = "1f63er20154oqhllurcb4qs9cn";
    public static final String COGNITO_USER_POOL_ID = "eu-west-1_eU9ClkneX";
    public static final String COGNITO_PUBLIC_KEYS_ADDRESS = "https://cognito-idp." + Constants.AWS_REGION + ".amazonaws.com/" + COGNITO_USER_POOL_ID + "/.well-known/jwks.json";

    public static final String SECRET_ID_SERVICE_ACCOUNT_KEY = "prod/firebase/serviceAccountKey";
    public static final String SECRET_ID_REPO_TOKEN_KEY = "/prod/firebase/repoTokenKey";

    public static final String SECRETS_MANAGER_ENDPOINT = "secretsmanager.eu-west-1.amazonaws.com";

    public static final String FIREBASE_DATABASE_URL = "https://repocleaner-db.firebaseio.com";

    public static final String HYSTRIX_GROUP_PUBLIC_KEYS = "PUBLIC_KEYS";
    public static final String HYSTRIX_GROUP_S3_DELETE = "S3_DELETE";
    public static final String HYSTRIX_GROUP_S3_DOWNLOAD = "S3_DOWNLOAD";
    public static final String HYSTRIX_GROUP_S3_UPLOAD = "S3_UPLOAD";
    public static final String HYSTRIX_GROUP_GET_SECRET = "GET_SECRET";
    public static final String HYSTRIX_GROUP_GET_PARAMETER = "GET_PARAMETER";
    public static final String HYSTRIX_GROUP_OPEN_CONNECTION = "OPEN_CONNECTION";
}
