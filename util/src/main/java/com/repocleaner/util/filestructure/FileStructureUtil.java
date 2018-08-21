package com.repocleaner.util.filestructure;

import com.repocleaner.util.RepoCleanerException;

import java.io.File;
import java.util.UUID;

public class FileStructureUtil {
    private static final File TMP_FOLDER = new File("/tmp");
    private static final File ZIPPED_FOLDER = new File(TMP_FOLDER, "zipped");
    private static final String INITIATOR_FILE = "initiator";
    private static final String SOURCE_FOLDER = "source";
    private static final String SINK_FILE = "sink.json";

    static {
        ZIPPED_FOLDER.mkdirs();
    }

    public static PrepareFileStructure createPrepareFileStructure(String requestId) throws RepoCleanerException {
        File rootFolder = new File(TMP_FOLDER, requestId);
        File initiatorFile = new File(rootFolder, INITIATOR_FILE);
        File sourceFolder = new File(rootFolder, SOURCE_FOLDER);
        File sinkFile = new File(rootFolder, SINK_FILE);
        File zippedFile = new File(ZIPPED_FOLDER, requestId);
        if (!sourceFolder.mkdirs()) {
            throw new RepoCleanerException("Failed to create source folder");
        }
        return new PrepareFileStructure(rootFolder, initiatorFile, sourceFolder, sinkFile, zippedFile);
    }

    public static CleanFileStructure createCleanFileStructure(String key) throws RepoCleanerException {
        File rootFolder = new File(TMP_FOLDER, key);
        File initiatorFile = new File(rootFolder, INITIATOR_FILE);
        File sourceFolder = new File(rootFolder, SOURCE_FOLDER);
        File zippedFile = new File(ZIPPED_FOLDER, key);
        if (!sourceFolder.mkdirs()) {
            throw new RepoCleanerException("Failed to create source folder");
        }
        return new CleanFileStructure(rootFolder, initiatorFile, sourceFolder, zippedFile);
    }

    public static SendFileStructure createSendFileStructure(String key) throws RepoCleanerException {
        File rootFolder = new File(TMP_FOLDER, key);
        File initiatorFile = new File(rootFolder, INITIATOR_FILE);
        File sourceFolder = new File(rootFolder, SOURCE_FOLDER);
        File sinkFile = new File(rootFolder, SINK_FILE);
        File zippedFile = new File(ZIPPED_FOLDER, key);
        String randomFileName = UUID.randomUUID().toString();
        File tempFile = new File(ZIPPED_FOLDER, randomFileName);
        if (!sourceFolder.mkdirs()) {
            throw new RepoCleanerException("Failed to create source folder");
        }
        return new SendFileStructure(rootFolder, initiatorFile, sourceFolder, sinkFile, zippedFile, tempFile);
    }
}
