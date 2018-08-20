package com.repocleaner.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;

public class ZipUtil {
    private static final String ZIP_PASSWORD = System.getenv("ZIP_PASSWORD");

    public static void extract(File zipped, File outputFolder) throws RepoCleanerException {
        try {
            ZipFile zipFile = new ZipFile(zipped);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(ZIP_PASSWORD);
            }
            zipFile.extractAll(outputFolder.getAbsolutePath());
        } catch (ZipException e) {
            throw new RepoCleanerException("Failed to extract to folder", e);
        }
    }

    public static void zip(File folder, File zippedFile) throws RepoCleanerException {
        try {
            ZipFile zipFile = new ZipFile(zippedFile);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFile(folder, parameters);
            zipFile.createZipFileFromFolder(folder, parameters, false, -1);
        } catch (
                ZipException e) {
            throw new RepoCleanerException("Failed to create zip file", e);
        }
    }
}
