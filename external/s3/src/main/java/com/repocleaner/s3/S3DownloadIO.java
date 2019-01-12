package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.DownloadIO;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public class S3DownloadIO extends S3LifecycleIO implements DownloadIO {
    public S3DownloadIO(S3Event event, JsonUtil jsonUtil) throws RepoCleanerException {
        super(event, jsonUtil);
    }

    @Override
    public void downloaded() throws RepoCleanerException {
        S3FileStructure.upload(getFileStructure(), Constants.BUCKET_DOWNLOADED);
    }
}
