package users;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.auth0.jwt.interfaces.DecodedJWT;
import users.domain.BoughtTokenTransaction;
import users.domain.CleanedRepo;
import users.domain.CleanedTokenTransaction;
import users.domain.Config;
import users.domain.HostedRepo;
import users.domain.TokenTransaction;
import users.rest.UserInfoRequest;
import users.rest.UserInfoResponse;
import users.security.AppInfo;
import users.security.Authenticator;
import users.security.Authoriser;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class UserInfoLambda implements RequestHandler<UserInfoRequest, UserInfoResponse> {
    private final Authenticator authenticator = new Authenticator();
    private final Authoriser authoriser = new Authoriser();

    public UserInfoResponse handleRequest(UserInfoRequest request, Context context) {
        if (!AppInfo.hasPublicKeys()) {
            return UserInfoResponse.EMPTY_RESPONSE;
        }
        DecodedJWT decoded = authenticator.authenticate(request.getJwt());
        if ((decoded == null) || !authoriser.isAuthorised(decoded)) {
            return UserInfoResponse.EMPTY_RESPONSE;
        }
        // TODO
        String email = decoded.getClaim("email").asString();
        int tokens = 123;
        List<TokenTransaction> transactions = new ArrayList<>();
        transactions.add(new BoughtTokenTransaction(1000, "USD", LocalDateTime.of(2018, Month.AUGUST, 17, 18, 35, 40)));
        transactions.add(new CleanedTokenTransaction(150, email + '_' + "https://github.com/USER/REPO/pull/PR_INDEX"));
        List<HostedRepo> repos = new ArrayList<>();
        List<CleanedRepo> cleanedRepos = new ArrayList<>();
        cleanedRepos.add(new CleanedRepo(new Config()));
        repos.add(new HostedRepo("github", "USER", false, new Config(), "*", cleanedRepos));
        Config config = new Config();
        return new UserInfoResponse(tokens, transactions, repos, config);
    }
}
