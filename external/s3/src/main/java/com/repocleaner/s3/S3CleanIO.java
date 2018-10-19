package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.CleanIO;
import com.repocleaner.model.CleanResult;
import com.repocleaner.model.FileStructure;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public class S3CleanIO extends S3LifecycleIO implements CleanIO {
    public S3CleanIO(S3Event event, JsonUtil jsonUtil) throws RepoCleanerException {
        super(event, jsonUtil);
    }

    @Override
    public void cleaned(CleanResult cleanResult) throws RepoCleanerException {
        FileStructure fileStructure = getFileStructure();
        fileStructure.setCleanResult(cleanResult);
        S3FileStructure.upload(fileStructure, Constants.BUCKET_CLEANED);
    }
}
