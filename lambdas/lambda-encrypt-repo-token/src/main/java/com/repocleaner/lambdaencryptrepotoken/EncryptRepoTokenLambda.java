package com.repocleaner.lambdaencryptrepotoken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.io.rest.EncryptRepoTokenLambdaRequest;
import com.repocleaner.io.rest.EncryptRepoTokenLambdaResponse;
import com.repocleaner.repotoken.RepoToken;
import com.repocleaner.util.RepoCleanerException;

public class EncryptRepoTokenLambda implements RequestHandler<EncryptRepoTokenLambdaRequest, EncryptRepoTokenLambdaResponse> {
    private static final RepoToken REPO_TOKEN = new RepoToken();

    @Override
    public EncryptRepoTokenLambdaResponse handleRequest(EncryptRepoTokenLambdaRequest request, Context context) {
        String token = request.getToken();
        try {
            String encryptedToken = REPO_TOKEN.encryptToken(token);
            return new EncryptRepoTokenLambdaResponse(true, encryptedToken, null);
        } catch (RepoCleanerException e) {
            return new EncryptRepoTokenLambdaResponse(false, null, e.getMessage());
        }
    }
}
