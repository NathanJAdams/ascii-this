package com.repocleaner.s3;

import com.repocleaner.io.external.ScheduleIO;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.Constants;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class S3ScheduleIO implements ScheduleIO {
    private final JsonUtil jsonUtil;

    @Override
    public void schedule(LifecycleRequest lifecycleRequest) throws RepoCleanerException {
        String id = lifecycleRequest.getId();
        FileStructure fileStructure = new FileStructure(S3Info.TMP_FOLDER, id, jsonUtil);
        fileStructure.setLifecycleRequest(lifecycleRequest);
        S3FileStructure.upload(fileStructure, Constants.BUCKET_WAITING);
    }
}
