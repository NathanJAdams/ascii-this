package com.repocleaner.cognito;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;

import java.util.Date;
import java.util.List;

public class Authoriser {
    public void authorise(DecodedJWT decoded) throws RepoCleanerException {
        Date now = new Date();
        if (decoded.getExpiresAt().before(now)) {
            throw new RepoCleanerException("Not authorised");
        }
        List<String> audience = decoded.getAudience();
        if ((audience == null) || audience.isEmpty()) {
            throw new RepoCleanerException("Not authorised");
        }
        if (!audience.contains(Constants.COGNITO_CLIENT_ID)) {
            throw new RepoCleanerException("Not authorised");
        }
    }
}
