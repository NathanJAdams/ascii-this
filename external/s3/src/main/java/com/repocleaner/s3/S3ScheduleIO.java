package com.repocleaner.s3;

import com.repocleaner.io.external.ScheduleIO;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.util.UUID;

public class S3ScheduleIO implements ScheduleIO {
    @Override
    public void schedule(LifecycleRequest lifecycleRequest, JsonUtil jsonUtil) throws RepoCleanerException {
        String id = UUID.randomUUID().toString();
        try (S3FileStructure fileStructure = new S3FileStructure(id)) {
            fileStructure.setLifecycleRequest(lifecycleRequest, jsonUtil);
            fileStructure.upload(Constants.BUCKET_WAITING);
        }
    }
}