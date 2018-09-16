package com.repocleaner.io.external;

import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public interface SendIO extends LifecycleIO {
    void send(JsonUtil jsonUtil) throws RepoCleanerException;
}
