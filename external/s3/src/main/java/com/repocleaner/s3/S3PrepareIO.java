package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.PrepareIO;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public class S3PrepareIO extends S3LifecycleIO implements PrepareIO {
    public S3PrepareIO(S3Event event, JsonUtil jsonUtil) throws RepoCleanerException {
        super(event, jsonUtil);
    }

    @Override
    public void prepared() throws RepoCleanerException {
        S3FileStructure.upload(getFileStructure(), Constants.BUCKET_PREPARED);
    }
}
