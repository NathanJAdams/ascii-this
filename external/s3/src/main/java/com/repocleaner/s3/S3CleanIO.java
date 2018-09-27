package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.external.CleanIO;
import com.repocleaner.util.CleanResult;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public class S3CleanIO extends S3LifecycleIO implements CleanIO {
    public S3CleanIO(S3Event event) {
        super(event);
    }

    @Override
    public void cleaned(CleanResult cleanResult, JsonUtil jsonUtil) throws RepoCleanerException {
        getFileStructure().setCleanResult(cleanResult, jsonUtil);
        getFileStructure().upload(Constants.BUCKET_CLEANED);
    }
}
