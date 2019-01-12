package com.repocleaner.sink;

import com.repocleaner.util.Constants;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.eclipse.jgit.api.Git;

public enum ZipResponseType {
    Jar {
        @Override
        public void saveTo(File sourceFolder, File zipFile) throws RepoCleanerException {
            ZipUtil.zip(sourceFolder, zipFile);
        }
    },
    Diff {
        @Override
        public void saveTo(File sourceFolder, File zipFile) throws RepoCleanerException {
            String diff;
            try (Git git = GitUtil.open(sourceFolder)) {
                diff = GitUtil.getLatestDiff(git);
            }
            File tempFolder = null;
            File diffFile = null;
            try {
                tempFolder = new File(sourceFolder, Constants.DIFF_FOLDER);
                tempFolder.mkdir();
                diffFile = new File(tempFolder, Constants.DIFF_FILE);
                diffFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(diffFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                Writer writer = new OutputStreamWriter(bos);
                writer.write(diff);
                ZipUtil.zip(tempFolder, zipFile);
            } catch (IOException e) {
                throw new RepoCleanerException("Failed to create diff file", e);
            } finally {
                if (diffFile != null) {
                    diffFile.delete();
                }
                if (tempFolder != null) {
                    tempFolder.delete();
                }
            }
        }
    };

    public static void main(String[] args) throws Exception {
        File sourceFolder = new File("C:/Users/Nathan/Desktop/verJ/verJ");
        try (Git git = GitUtil.open(sourceFolder)) {
            String diff = GitUtil.getLatestDiff(git);
            System.out.println(diff);
        }
    }

    public abstract void saveTo(File sourceFolder, File zipFile) throws RepoCleanerException;
}
