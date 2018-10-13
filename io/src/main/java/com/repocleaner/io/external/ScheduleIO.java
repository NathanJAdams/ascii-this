package com.repocleaner.io.external;

import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public interface ScheduleIO {
    void schedule(LifecycleRequest lifecycleRequest, JsonUtil jsonUtil) throws RepoCleanerException;
}
