package com.repocleaner.io.external;

import com.repocleaner.util.CleanResult;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

public interface CleanIO extends LifecycleIO {
    void cleaned(CleanResult cleanResult, JsonUtil jsonUtil) throws RepoCleanerException;
}
