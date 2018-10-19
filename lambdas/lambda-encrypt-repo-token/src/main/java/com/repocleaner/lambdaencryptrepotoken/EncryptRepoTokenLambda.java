package com.repocleaner.lambdaencryptrepotoken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.corerepotoken.RepoToken;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.rest.ResponseUtil;

public class EncryptRepoTokenLambda implements RequestHandler<EncryptRepoTokenRequest, EncryptRepoTokenResponse> {
    private static final RepoToken REPO_TOKEN = new RepoToken();

    @Override
    public EncryptRepoTokenResponse handleRequest(EncryptRepoTokenRequest request, Context context) {
        request.preCheck();
        String token = request.getToken();
        String encryptedToken;
        try {
            encryptedToken = REPO_TOKEN.encryptToken(token);
            return new EncryptRepoTokenResponse(encryptedToken);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.internalError("Encrypt token");
        }
    }
}
