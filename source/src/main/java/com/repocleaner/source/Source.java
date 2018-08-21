package com.repocleaner.source;

import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public interface Source<T> {
    T saveSourceGetSink(File sourceFolder) throws RepoCleanerException;
}
