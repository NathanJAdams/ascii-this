package com.repocleaner.io;

import com.repocleaner.model.CleanResult;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.model.Sink;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface SendIO extends LifecycleIO {
    default void send() throws RepoCleanerException {
        FileStructure fileStructure = getFileStructure();
        LifecycleRequest lifecycleRequest = fileStructure.getLifecycleRequest();
        Sink sink = lifecycleRequest.getSink();
        File codeFolder = fileStructure.getCodeFolder();
        CleanResult cleanResult = fileStructure.getCleanResult();
        File tempFolder = fileStructure.getTempFolder();
        sink.upload(codeFolder, cleanResult, tempFolder);
    }
}
