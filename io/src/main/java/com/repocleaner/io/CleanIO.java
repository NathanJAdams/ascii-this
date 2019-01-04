package com.repocleaner.io;

import com.repocleaner.model.CleanResult;
import com.repocleaner.util.RepoCleanerException;

public interface CleanIO extends LifecycleIO {
    void cleaned(CleanResult cleanResult) throws RepoCleanerException;
}