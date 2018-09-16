package com.repocleaner.io.external;

import com.repocleaner.util.RepoCleanerException;

public interface PrepareIO extends LifecycleIO {
    void prepared() throws RepoCleanerException;
}
