package com.repocleaner.coredownload;

import com.repocleaner.io.DownloadIO;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.CleanRequest;
import com.repocleaner.model.Source;
import com.repocleaner.util.RepoCleanerException;

import java.io.File;

public class Downloader {
    public static void download(DownloadIO downloadIO) throws RepoCleanerException {
        FileStructure fileStructure = downloadIO.getFileStructure();
        File codeFolder = fileStructure.getCodeFolder();
        CleanRequest cleanRequest = fileStructure.getLifecycleRequest();
        Source source = cleanRequest.getSource();
        source.download(codeFolder);
        downloadIO.downloaded();
    }
}
