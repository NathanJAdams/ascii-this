package com.repocleaner.send;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.s3.S3FileTransferer;
import com.repocleaner.userinfo.UserInfo;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;
import java.util.List;

public class SendRepoLambda implements RequestHandler<S3Event, Void> {
    @Override
    public Void handleRequest(S3Event event, Context context) {
        File folder = new File("/tmp");
        List<File> files = S3FileTransferer.transfer(event, folder);
        for (File file : files) {
            try {
                UserInfo userInfo = Sender.send(file);
                // get cleaning charge
                long tokenCost = 504584859048L;
                userInfo.setTokens(userInfo.getTokens() - tokenCost);
                // TODO save user info
            } catch (RepoCleanerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
