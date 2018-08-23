package com.repocleaner.source;

import com.repocleaner.util.CleanResult;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface Source {
    void saveSource(File sourceFolder) throws RepoCleanerException;

    void sendCleaned(File sourceFolder, CleanResult cleanResult, File tempFile) throws RepoCleanerException;
}
