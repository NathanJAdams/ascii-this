package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.external.PrepareIO;
import com.repocleaner.util.RepoCleanerException;

public class S3PrepareIO extends S3LifecycleIO implements PrepareIO {
    public S3PrepareIO(S3Event event) {
        super(event);
    }

    @Override
    public void prepared() throws RepoCleanerException {
        getFileStructure().upload(S3Info.PREPARED_BUCKET);
    }
}
