package com.repocleaner.model;

import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;
import lombok.Getter;

import java.io.File;

public class FileStructure implements AutoCloseable {
    private static final String ROOT_PREFIX = "root_";
    private static final String TEMP_PREFIX = "temp_";
    private static final String LIFECYCLE_REQUEST = "lifecycle-request";
    private static final String CLEAN_RESULT = "clean-result";

    @Getter
    private final String key;
    private final JsonUtil jsonUtil;
    @Getter
    private final File rootFolder;
    @Getter
    private final File tempFolder;
    @Getter
    private final File codeFolder;
    private final File lifecycleRequestFile;
    private final File cleanResultFile;

    public FileStructure(File rootParentFolder, String key, JsonUtil jsonUtil) {
        this.key = key;
        this.jsonUtil = jsonUtil;
        this.rootFolder = new File(rootParentFolder, ROOT_PREFIX + key);
        rootFolder.mkdir();
        tempFolder = new File(rootParentFolder, TEMP_PREFIX + key);
        codeFolder = new File(rootFolder, "code");
        this.lifecycleRequestFile = new File(rootFolder, LIFECYCLE_REQUEST);
        this.cleanResultFile = new File(rootFolder, CLEAN_RESULT);
    }

    public LifecycleRequest getLifecycleRequest() throws RepoCleanerException {
        return jsonUtil.fromJsonFileOrNull(lifecycleRequestFile, LifecycleRequest.class);
    }

    public void setLifecycleRequest(LifecycleRequest lifecycleRequest) throws RepoCleanerException {
        jsonUtil.toJsonFile(lifecycleRequest, lifecycleRequestFile);
    }

    public CleanResult getCleanResult() throws RepoCleanerException {
        return jsonUtil.fromJsonFileOrNull(cleanResultFile, CleanResult.class);
    }

    public void setCleanResult(CleanResult cleanResult) throws RepoCleanerException {
        jsonUtil.toJsonFile(cleanResult, cleanResultFile);
    }

    @Override
    public void close() {
        deleteRecursively(rootFolder);
        deleteRecursively(tempFolder);
    }

    private void deleteRecursively(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File subFile : files) {
                        deleteRecursively(subFile);
                    }
                }
            }
            file.delete();
        }
    }
}
