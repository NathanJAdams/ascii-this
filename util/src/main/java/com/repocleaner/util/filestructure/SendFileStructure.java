package com.repocleaner.util.filestructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@AllArgsConstructor
@Getter
public class SendFileStructure implements AutoCloseable {
    private final File rootFolder;
    private final File initiatorFile;
    private final File sourceFolder;
    private final File sinkFile;
    private final File zippedFile;
    private final File tempFile;

    @Override
    public void close() {
        rootFolder.delete();
        zippedFile.delete();
        tempFile.delete();
    }
}
