package com.repocleaner.lambdaencryptrepotoken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.core.encrypt.RepoTokenEncryptor;
import com.repocleaner.core.util.Constants;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.core.util.rest.ResponseUtil;
import com.repocleaner.secrets.SecretCommander;

public class EncryptRepoTokenLambda implements RequestHandler<EncryptRepoTokenRequest, EncryptRepoTokenResponse> {
    private static final RepoTokenEncryptor REPO_TOKEN_ENCRYPTOR = new RepoTokenEncryptor(SecretCommander.getParameter(Constants.SECRET_ID_REPO_TOKEN_KEY));

    @Override
    public EncryptRepoTokenResponse handleRequest(EncryptRepoTokenRequest request, Context context) {
        request.preCheck();
        String token = request.getToken();
        String encryptedToken;
        try {
            encryptedToken = REPO_TOKEN_ENCRYPTOR.encryptToken(token);
            return new EncryptRepoTokenResponse(encryptedToken);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.internalError("Encrypt token");
        }
    }
}
