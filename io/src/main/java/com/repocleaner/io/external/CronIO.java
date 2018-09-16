package com.repocleaner.io.external;

import com.repocleaner.model.receive.LifecycleRequest;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public interface CronIO {
    void waiting(LifecycleRequest lifecycleRequest, JsonUtil jsonUtil) throws RepoCleanerException;
}
