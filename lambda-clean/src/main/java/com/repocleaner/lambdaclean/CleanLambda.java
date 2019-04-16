package com.repocleaner.lambdaclean;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.cleaner.Cleaner;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.core.util.json.JsonUtil;
import com.repocleaner.s3.S3CleanIO;

public class CleanLambda implements RequestHandler<S3Event, Void> {
    private static final Cleaner CLEANER = new Cleaner();

    public Void handleRequest(S3Event event, Context context) {
        try (S3CleanIO cleanIO = new S3CleanIO(event, new JsonUtil())) {
            System.out.println("Start cleaning");
            CLEANER.clean(cleanIO);
            System.out.println("Finished cleaning");
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
