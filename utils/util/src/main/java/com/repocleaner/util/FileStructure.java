package com.repocleaner.util;

import lombok.Getter;

import java.io.File;

@Getter
public class FileStructure implements AutoCloseable {
    private static final File TMP_FOLDER = new File("/tmp");

    private final File rootFolder;
    private final File codeFolder;
    private final File initiatorFile;
    private final File configFile;
    private final File sourceFile;
    private final File tokenFile;
    private final File tokenCostFile;
    private final File zippedFile;
    private final File tempFile;

    public FileStructure(String root) {
        this.rootFolder = new File(TMP_FOLDER, root);
        this.codeFolder = new File(rootFolder, "code");
        this.initiatorFile = new File(rootFolder, "initiator");
        this.configFile = new File(rootFolder, "config");
        this.sourceFile = new File(rootFolder, "source");
        this.tokenFile = new File(rootFolder, "token");
        this.tokenCostFile = new File(rootFolder, "token-cost");
        this.zippedFile = new File(TMP_FOLDER, "zipped_" + root);
        this.tempFile = new File(TMP_FOLDER, "temp_" + root);
        codeFolder.mkdirs();
    }

    @Override
    public void close() {
        rootFolder.delete();
        zippedFile.delete();
        tempFile.delete();
    }
}
