package com.repocleaner.coreprepare;

import com.repocleaner.io.external.PrepareIO;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.model.Source;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public class Preparer {
    public static void prepare(PrepareIO prepareIO) throws RepoCleanerException {
        FileStructure fileStructure = prepareIO.getFileStructure();
        File codeFolder = fileStructure.getCodeFolder();
        LifecycleRequest lifecycleRequest = fileStructure.getLifecycleRequest();
        Source source = lifecycleRequest.getSource();
        source.download(codeFolder);
        prepareIO.prepared();
    }
}
