package com.repocleaner.send;

import com.repocleaner.io.external.SendIO;
import com.repocleaner.source.SourceGsonCustomiser;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public class Sender {
    private static final JsonUtil JSON_UTIL = new JsonUtil(new SourceGsonCustomiser());

    public static void send(SendIO sendIO) throws RepoCleanerException {
        sendIO.send(JSON_UTIL);
    }
}
