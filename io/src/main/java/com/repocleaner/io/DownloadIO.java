package com.repocleaner.io;

import com.repocleaner.util.RepoCleanerException;

public interface DownloadIO extends LifecycleIO {
    void downloaded() throws RepoCleanerException;
}
