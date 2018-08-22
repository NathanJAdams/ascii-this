package com.repocleaner.source;

import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import org.eclipse.jgit.api.Git;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
            try {
                File tempFolder = File.createTempFile("diff", "", sourceFolder);
                File diffFile = new File(tempFolder, "diff.txt");
                try (Git git = GitUtil.init(sourceFolder);
                     FileOutputStream fos = new FileOutputStream(diffFile);
                     BufferedOutputStream bos = new BufferedOutputStream(fos);
                     Writer writer = new OutputStreamWriter(bos)) {
                    String diff = GitUtil.getLatestDiff(git);
                    writer.write(diff);
                    ZipUtil.zip(tempFolder, zipFile);
                    tempFolder.delete();
                }
            } catch (IOException e) {
                throw new RepoCleanerException("Failed to create diff file", e);
            }
        }
    };

    public abstract void saveTo(File sourceFolder, File zipFile) throws RepoCleanerException;
}
