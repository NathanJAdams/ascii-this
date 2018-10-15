package com.repocleaner.io.external;

import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.RepoCleanerException;

public interface ScheduleIO {
    void schedule(LifecycleRequest lifecycleRequest) throws RepoCleanerException;
}
