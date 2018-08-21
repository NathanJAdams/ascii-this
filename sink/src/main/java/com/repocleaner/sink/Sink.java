package com.repocleaner.sink;

import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface Sink {
    void sendSource(File sourceFolder, File tempFile) throws RepoCleanerException;
}
