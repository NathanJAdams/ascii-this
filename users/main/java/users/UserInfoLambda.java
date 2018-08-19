package users;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.firebase.database.DatabaseReference;
import users.db.DatabaseReferenceCreator;
import users.db.DbSelector;
import users.domain.UserInfo;
import users.rest.UserInfoRequest;
import users.rest.UserInfoResponse;
import users.security.AppInfo;
import users.security.Authenticator;
import users.security.Authoriser;

public class UserInfoLambda implements RequestHandler<UserInfoRequest, UserInfoResponse> {
    private static final Authenticator AUTHENTICATOR = new Authenticator();
    private static final Authoriser AUTHORISER = new Authoriser();
    private static final DatabaseReference USERS_REF = DatabaseReferenceCreator.getDbConnection().child("users");

    public UserInfoResponse handleRequest(UserInfoRequest request, Context context) {
        if (!AppInfo.hasPublicKeys()) {
            return UserInfoResponse.EMPTY_RESPONSE;
        }
        DecodedJWT decoded = AUTHENTICATOR.authenticate(request.getJwt());
        if ((decoded == null) || !AUTHORISER.isAuthorised(decoded)) {
            return UserInfoResponse.EMPTY_RESPONSE;
        }
        String email = decoded.getClaim("email").asString();
        UserInfo userInfo = new DbSelector<>(USERS_REF, UserInfo.class, email, true).get();
        return new UserInfoResponse(userInfo);
    }
}
