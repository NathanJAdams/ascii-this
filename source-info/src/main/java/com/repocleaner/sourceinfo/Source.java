package com.repocleaner.sourceinfo;

import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface Source<T> {
    T saveSourceGetSinkDetails(File sourceFolder) throws RepoCleanerException;
}
