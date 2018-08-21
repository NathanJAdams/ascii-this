package com.repocleaner.util.filestructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@AllArgsConstructor
@Getter
public class CleanFileStructure implements AutoCloseable {
    private final File rootFolder;
    private final File initiatorFile;
    private final File configFile;
    private final File sourceFolder;
    private final File tokenCostFile;
    private final File zippedFile;

    @Override
    public void close() {
        rootFolder.delete();
        zippedFile.delete();
    }
}
