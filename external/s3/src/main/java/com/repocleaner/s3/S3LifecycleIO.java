package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.LifecycleIO;
import com.repocleaner.model.FileStructure;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;
import lombok.Getter;

public abstract class S3LifecycleIO implements LifecycleIO {
    @Getter
    private final FileStructure fileStructure;

    public S3LifecycleIO(S3Event event, JsonUtil jsonUtil) throws RepoCleanerException {
        this.fileStructure = S3FileStructure.download(event, jsonUtil);
    }
}
