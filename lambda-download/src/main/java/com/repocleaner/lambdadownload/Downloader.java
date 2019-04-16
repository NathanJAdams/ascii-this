package com.repocleaner.lambdadownload;

import com.repocleaner.core.model.CleanRequest;
import com.repocleaner.core.model.FileStructure;
import com.repocleaner.core.model.Source;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.s3.S3DownloadIO;
import java.io.File;

public class Downloader {
    public void download(S3DownloadIO downloadIO) throws RepoCleanerException {
        FileStructure fileStructure = downloadIO.getFileStructure();
        File codeFolder = fileStructure.getCodeFolder();
        CleanRequest cleanRequest = fileStructure.getLifecycleRequest();
        Source source = cleanRequest.getSource();
        source.download(codeFolder);
        downloadIO.downloaded();
    }
}
