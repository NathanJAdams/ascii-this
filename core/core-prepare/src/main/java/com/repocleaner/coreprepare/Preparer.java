package com.repocleaner.coreprepare;

import com.repocleaner.initiator.InitiatorGsonCustomiser;
import com.repocleaner.io.external.PrepareIO;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.model.Source;
import com.repocleaner.sink.SinkGsonCustomiser;
import com.repocleaner.source.SourceGsonCustomiser;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.io.File;

public class Preparer {
    private static final JsonUtil JSON_UTIL = new JsonUtil(new InitiatorGsonCustomiser(), new SinkGsonCustomiser(), new SourceGsonCustomiser());

    public static void prepare(PrepareIO prepareIO) throws RepoCleanerException {
        File codeFolder = prepareIO.getCodeFolder();
        LifecycleRequest lifecycleRequest = prepareIO.getLifecycleRequest(JSON_UTIL);
        Source source = lifecycleRequest.getSource();
        source.download(codeFolder);
        prepareIO.prepared();
    }
}
