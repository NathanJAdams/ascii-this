package com.repocleaner.prepare;

import com.repocleaner.initiator.InitiatorGsonCustomiser;
import com.repocleaner.io.external.PrepareIO;
import com.repocleaner.source.SourceGsonCustomiser;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.io.File;

public class Preparer {
    private static final JsonUtil JSON_UTIL = new JsonUtil(new SourceGsonCustomiser(),new InitiatorGsonCustomiser());

    public static void prepare(PrepareIO prepareIO) throws RepoCleanerException {
        File codeFolder = prepareIO.getCodeFolder();
        prepareIO.getLifecycleRequest(JSON_UTIL).getSource().download(codeFolder);
        prepareIO.prepared();
    }
}
