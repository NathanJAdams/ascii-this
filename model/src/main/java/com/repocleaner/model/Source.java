package com.repocleaner.model;

import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface Source {
    void download(File sourceFolder) throws RepoCleanerException;
}
