package com.repocleaner.cognito;

import com.auth0.jwt.interfaces.DecodedJWT;

public class CognitoValidator {
    private static final Authenticator AUTHENTICATOR = new Authenticator();
    private static final Authoriser AUTHORISER = new Authoriser();

    public static String getValidEmail(String jwt) {
        if (!CognitoInfo.hasPublicKeys()) {
            return null;
        }
        DecodedJWT decoded = AUTHENTICATOR.authenticate(jwt);
        if ((decoded == null) || !AUTHORISER.isAuthorised(decoded)) {
            return null;
        }
        return decoded.getClaim("email").asString();
    }
}
