package com.repocleaner.io;

import com.repocleaner.model.CleanResult;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.CleanRequest;
import com.repocleaner.model.Sink;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface SendIO extends LifecycleIO {
    default void send() throws RepoCleanerException {
        FileStructure fileStructure = getFileStructure();
        CleanRequest cleanRequest = fileStructure.getLifecycleRequest();
        Sink sink = cleanRequest.getSink();
        File codeFolder = fileStructure.getCodeFolder();
        CleanResult cleanResult = fileStructure.getCleanResult();
        File tempFolder = fileStructure.getTempFolder();
        sink.upload(codeFolder, cleanResult, tempFolder);
    }
}
