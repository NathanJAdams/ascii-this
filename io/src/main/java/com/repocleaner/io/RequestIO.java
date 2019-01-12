package com.repocleaner.io;

import com.repocleaner.model.CleanRequest;
import com.repocleaner.util.RepoCleanerException;

public interface RequestIO {
    void request(CleanRequest cleanRequest) throws RepoCleanerException;
}
