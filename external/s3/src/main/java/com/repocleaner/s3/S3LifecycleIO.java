package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.external.LifecycleIO;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public abstract class S3LifecycleIO implements LifecycleIO {
    private final S3Event event;

    @Getter
    private S3FileStructure fileStructure;

    @Override
    public final File getCodeFolder() throws RepoCleanerException {
        ensureFileStructure();
        return fileStructure.getCodeFolder();
    }

    @Override
    public final LifecycleRequest getLifecycleRequest(JsonUtil jsonUtil) throws RepoCleanerException {
        ensureFileStructure();
        return fileStructure.getLifecycleRequest(jsonUtil);
    }

    private void ensureFileStructure() throws RepoCleanerException {
        if (fileStructure == null) {
            this.fileStructure = S3EventUnwrapper.download(event);
        }
    }
}
