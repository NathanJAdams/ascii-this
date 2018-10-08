package com.repocleaner.cognito;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.repocleaner.util.Constants;

import java.util.Date;
import java.util.List;

public class Authoriser {
    public boolean isAuthorised(DecodedJWT decoded) {
        Date now = new Date();
        if (decoded.getExpiresAt().before(now)) {
            return false;
        }
        List<String> audience = decoded.getAudience();
        if ((audience == null) || audience.isEmpty()) {
            return false;
        }
        if (!audience.contains(Constants.COGNITO_CLIENT_ID)) {
            return false;
        }
        return true;
    }
}
