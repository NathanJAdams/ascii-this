package main.com.repocleaner.lambdasetrepotoken;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.firebase.FirebaseUserIO;
import com.repocleaner.io.external.KeyIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.io.rest.SetRepoTokenLambdaRequest;
import com.repocleaner.io.rest.SetRepoTokenLambdaResponse;
import com.repocleaner.repotoken.RepoToken;
import com.repocleaner.s3.S3KeyIO;
import com.repocleaner.util.RepoCleanerException;

public class SetRepoTokenLambda implements RequestHandler<SetRepoTokenLambdaRequest, SetRepoTokenLambdaResponse> {
    private static final KeyIO KEY_IO = new S3KeyIO();
    private static final UserIO USER_IO = new FirebaseUserIO();

    @Override
    public SetRepoTokenLambdaResponse handleRequest(SetRepoTokenLambdaRequest request, Context context) {
        String userId = request.getUserId();
        String token = request.getToken();
        try {
            boolean success = RepoToken.encryptToken(KEY_IO, USER_IO, userId, token);
            if (success) {
                return SetRepoTokenLambdaResponse.SUCCESS;
            } else {
                return new SetRepoTokenLambdaResponse(false, "Failed to set repo token");
            }
        } catch (RepoCleanerException e) {
            return new SetRepoTokenLambdaResponse(false, e.getMessage());
        }
    }
}
