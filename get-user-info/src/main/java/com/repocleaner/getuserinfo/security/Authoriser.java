package com.repocleaner.getuserinfo.security;

import com.auth0.jwt.interfaces.DecodedJWT;

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
        return audience.contains(AppInfo.CLIENT_ID);
    }
}
