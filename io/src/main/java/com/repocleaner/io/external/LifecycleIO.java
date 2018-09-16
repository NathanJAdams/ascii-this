package com.repocleaner.io.external;

import com.repocleaner.model.receive.LifecycleRequest;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.io.File;

public interface LifecycleIO {
    File getCodeFolder() throws RepoCleanerException;

    LifecycleRequest getLifecycleRequest(JsonUtil jsonUtil) throws RepoCleanerException;
}
