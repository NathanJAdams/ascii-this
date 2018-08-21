package com.repocleaner.sourceinfo;

import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface Source<T> {
    T saveSourceGetSink(File sourceFolder) throws RepoCleanerException;
}
