package com.repocleaner.s3;

import com.repocleaner.io.external.CronIO;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.util.UUID;

public class S3CronIO implements CronIO {
    @Override
    public void waiting(LifecycleRequest lifecycleRequest, JsonUtil jsonUtil) throws RepoCleanerException {
        String id = UUID.randomUUID().toString();
        S3FileStructure fileStructure = new S3FileStructure(id);
        fileStructure.setLifecycleRequest(lifecycleRequest, jsonUtil);
        fileStructure.upload(S3Info.WAITING_BUCKET);
    }
}
