package com.repocleaner.s3;

import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.util.CleanResult;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.JsonUtil;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class S3FileStructure implements AutoCloseable {
    private static final File TMP_FOLDER = new File("/tmp");
    private static final String ROOT_PREFIX="root_";
    private static final String TEMP_PREFIX="temp_";
    private static final String LIFECYCLE_REQUEST="lifecycle-request";
    private static final String CLEAN_RESULT="clean-result";
    private static final String TOKEN="token";

    private final String key;

    private File zippedInputFolder;
    private File rootFolder;
    private File zippedOutputFolder;

    private File codeFolder;
    private File tempFolder;

    private LifecycleRequest lifecycleRequest;
    private String token;
    private CleanResult cleanResult;

    public void download(String bucket) throws RepoCleanerException {
        if (isUnzipped()) {
            throw new RepoCleanerException("Already downloaded");
        }
        ensureRootFolder();
        zippedInputFolder = new File(TMP_FOLDER, "zippedInput_" + bucket + "_" + key);
        S3FileActions.download(bucket, key, zippedInputFolder);
        ZipUtil.extract(zippedInputFolder, rootFolder);
        S3FileActions.delete(bucket, key);
        zippedInputFolder.delete();
    }

    public void upload(String bucket) throws RepoCleanerException {
        if (isZipped()) {
            throw new RepoCleanerException("Already uploaded");
        }
        ensureRootFolder();
        zippedOutputFolder = new File(TMP_FOLDER, "zippedOutput_" + bucket + "_" + key);
        ZipUtil.zip(rootFolder, zippedOutputFolder);
        S3FileActions.upload(bucket, key, zippedOutputFolder);
        zippedOutputFolder.delete();
    }

    public File getCodeFolder() {
        if (codeFolder == null) {
            ensureRootFolder();
            codeFolder = new File(rootFolder, "code");
        }
        return codeFolder;
    }

    public File getTempFolder() {
        if (tempFolder == null) {
            tempFolder = new File(TMP_FOLDER, TEMP_PREFIX + key);
        }
        return tempFolder;
    }

    public LifecycleRequest getLifecycleRequest(JsonUtil jsonUtil) throws RepoCleanerException {
        if (lifecycleRequest == null) {
            lifecycleRequest = get(LifecycleRequest.class, jsonUtil, LIFECYCLE_REQUEST);
        }
        return lifecycleRequest;
    }

    public void setLifecycleRequest(LifecycleRequest lifecycleRequest, JsonUtil jsonUtil) throws RepoCleanerException {
        set(lifecycleRequest, jsonUtil, LIFECYCLE_REQUEST);
    }

    public String getToken(JsonUtil jsonUtil) throws RepoCleanerException {
        if (token == null) {
            token = get(String.class, jsonUtil, TOKEN);
        }
        return token;
    }

    public void setToken(String token, JsonUtil jsonUtil) throws RepoCleanerException {
        set(token, jsonUtil, TOKEN);
    }

    public CleanResult getCleanResult(JsonUtil jsonUtil) throws RepoCleanerException {
        if (cleanResult == null) {
            cleanResult = get(CleanResult.class, jsonUtil, CLEAN_RESULT);
        }
        return cleanResult;
    }

    public void setCleanResult(CleanResult cleanResult, JsonUtil jsonUtil) throws RepoCleanerException {
        set(cleanResult, jsonUtil, CLEAN_RESULT);
    }

    private <T> T get(Class<T> tClass, JsonUtil jsonUtil, String name) throws RepoCleanerException {
        if (!isUnzipped()) {
            throw new RepoCleanerException("Not unzipped");
        }
        File file = new File(rootFolder, name);
        return jsonUtil.fromJsonFileOrNull(file, tClass);
    }

    private <T> void set(T t, JsonUtil jsonUtil, String name) throws RepoCleanerException {
        if (isZipped()) {
            throw new RepoCleanerException("Already zipped");
        }
        ensureRootFolder();
        File file = new File(rootFolder, name);
        jsonUtil.toJsonFile(t, file);
    }

    private boolean isUnzipped() {
        return (zippedInputFolder != null);
    }

    private boolean isZipped() {
        return (zippedOutputFolder != null);
    }

    private void ensureRootFolder() {
        if (rootFolder == null) {
            rootFolder = new File(TMP_FOLDER, ROOT_PREFIX + key);
            rootFolder.mkdir();
        }
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
