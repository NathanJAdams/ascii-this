package com.repocleaner.lambdaclean;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.coreclean.Cleaner;
import com.repocleaner.json.JsonInfo;
import com.repocleaner.s3.S3CleanIO;
import com.repocleaner.util.RepoCleanerException;

public class CleanLambda implements RequestHandler<S3Event, Void> {
    public Void handleRequest(S3Event event, Context context) {
        try (S3CleanIO cleanIO = new S3CleanIO(event, JsonInfo.JSON_UTIL)) {
            Cleaner.clean(cleanIO);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
