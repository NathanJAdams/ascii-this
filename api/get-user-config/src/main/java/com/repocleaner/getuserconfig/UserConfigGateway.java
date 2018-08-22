package com.repocleaner.getuserconfig;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.repocleaner.config.UserConfig;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbSelector;
import com.repocleaner.getuserconfig.security.AppInfo;
import com.repocleaner.getuserconfig.security.Authenticator;
import com.repocleaner.getuserconfig.security.Authoriser;

public class UserConfigGateway {
    private static final Authenticator AUTHENTICATOR = new Authenticator();
    private static final Authoriser AUTHORISER = new Authoriser();

    public static UserConfig getUserConfig(String jwt) {
        if (!AppInfo.hasPublicKeys()) {
            return null;
        }
        DecodedJWT decoded = AUTHENTICATOR.authenticate(jwt);
        if ((decoded == null) || !AUTHORISER.isAuthorised(decoded)) {
            return null;
        }
        String email = decoded.getClaim("email").asString();
        return new DbSelector<>(DatabaseReferenceCreator.USERS_REF, UserConfig.class, email, true).get();
    }
}
