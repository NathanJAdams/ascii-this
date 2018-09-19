package com.repocleaner.s3;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.io.external.SendIO;
import com.repocleaner.util.CleanResult;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.io.File;

public class S3SendIO extends S3LifecycleIO implements SendIO {
    public S3SendIO(S3Event event) {
        super(event);
    }

    @Override
    public void send(JsonUtil jsonUtil) throws RepoCleanerException {
        File codeFolder = getCodeFolder();
        CleanResult cleanResult = getFileStructure().getCleanResult(jsonUtil);
        File tempFolder = getFileStructure().getTempFolder();
        getLifecycleRequest(jsonUtil).getSink().upload(codeFolder, cleanResult, tempFolder);
    }
}
