package com.repocleaner.cognito;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.repocleaner.util.RepoCleanerException;

public class CognitoValidator {
    private static final Authenticator AUTHENTICATOR = new Authenticator();
    private static final Authoriser AUTHORISER = new Authoriser();

    public static String getValidEmail(String jwt) throws RepoCleanerException {
        DecodedJWT decoded = AUTHENTICATOR.authenticate(jwt);
        AUTHORISER.authorise(decoded);
        return decoded.getClaim("email").asString();
    }
}
