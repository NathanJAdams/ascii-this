package com.repocleaner.io;

import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.RepoCleanerException;

public interface ScheduleIO {
    void schedule(LifecycleRequest lifecycleRequest) throws RepoCleanerException;
}
