package com.repocleaner.getuserinfo;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.repocleaner.getuserinfo.security.AppInfo;
import com.repocleaner.getuserinfo.security.Authenticator;
import com.repocleaner.getuserinfo.security.Authoriser;
import com.repocleaner.userdb.DatabaseReferenceCreator;
import com.repocleaner.userdb.DbSelector;
import com.repocleaner.userinfo.UserInfo;

public class UserInfoGateway {
    private static final Authenticator AUTHENTICATOR = new Authenticator();
    private static final Authoriser AUTHORISER = new Authoriser();

    public static UserInfo getUserInfo(String jwt) {
        if (!AppInfo.hasPublicKeys()) {
            return null;
        }
        DecodedJWT decoded = AUTHENTICATOR.authenticate(jwt);
        if ((decoded == null) || !AUTHORISER.isAuthorised(decoded)) {
            return null;
        }
        String email = decoded.getClaim("email").asString();
        return new DbSelector<>(DatabaseReferenceCreator.USERS_REF, UserInfo.class, email, true).get();
    }
}
