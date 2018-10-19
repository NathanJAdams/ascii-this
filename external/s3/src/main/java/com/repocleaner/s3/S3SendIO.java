package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.SendIO;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public class S3SendIO extends S3LifecycleIO implements SendIO {
    public S3SendIO(S3Event event, JsonUtil jsonUtil) throws RepoCleanerException {
        super(event, jsonUtil);
    }
}
