package com.repocleaner.lambdaencryptrepotoken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.corerepotoken.RepoToken;
import com.repocleaner.io.rest.EncryptRepoTokenRequest;
import com.repocleaner.io.rest.EncryptRepoTokenResponse;
import com.repocleaner.io.rest.ResponseInfo;
import com.repocleaner.util.RepoCleanerException;

public class EncryptRepoTokenLambda implements RequestHandler<EncryptRepoTokenRequest, EncryptRepoTokenResponse> {
    private static final RepoToken REPO_TOKEN = new RepoToken();

    @Override
    public EncryptRepoTokenResponse handleRequest(EncryptRepoTokenRequest request, Context context) {
        String token = request.getToken();
        String encryptedToken;
        boolean success;
        String message;
        try {
            encryptedToken = REPO_TOKEN.encryptToken(token);
            success = true;
            message = "Successfully encrypted token";
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            success = false;
            message = "Failed to encrypt token";
            encryptedToken = null;
        }
        ResponseInfo responseInfo = new ResponseInfo(success, message);
        return new EncryptRepoTokenResponse(responseInfo, encryptedToken);
    }
}
