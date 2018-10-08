package com.repocleaner.cognito;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.repocleaner.util.RepoCleanerException;

public class CognitoValidator {
    private static final String EMAIL_CLAIM = "email";
    private static final String USER_ID_CLAIM = "sub";
    private static final Authenticator AUTHENTICATOR = new Authenticator();
    private static final Authoriser AUTHORISER = new Authoriser();

    public static DecodedJWT authenticate(String jwt) throws RepoCleanerException {
        return AUTHENTICATOR.authenticate(jwt);
    }

    public static boolean isAuthorised(DecodedJWT decoded) {
        return AUTHORISER.isAuthorised(decoded);
    }

    public static String getEmail(DecodedJWT decoded) {
        return getClaim(decoded, EMAIL_CLAIM);
    }

    public static String getUserID(DecodedJWT decoded) {
        return getClaim(decoded, USER_ID_CLAIM);
    }

    private static String getClaim(DecodedJWT decoded, String claim) {
        return decoded.getClaim(claim).asString();
    }
}
