package com.repocleaner.lambdaprepare;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.prepare.Preparer;
import com.repocleaner.s3.S3PrepareIO;
import com.repocleaner.util.RepoCleanerException;

public class PrepareLambda implements RequestHandler<S3Event, Void> {
    public Void handleRequest(S3Event event, Context context) {
        try {
            try (S3PrepareIO prepareIO = new S3PrepareIO(event)) {
                Preparer.prepare(prepareIO);
            }
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
