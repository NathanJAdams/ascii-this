package com.repocleaner.model;

import com.repocleaner.util.CleanResult;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface Sink {
    void upload(File codeFolder, CleanResult cleanResult, File tempFolder) throws RepoCleanerException;
}
