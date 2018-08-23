package com.repocleaner.source;

import com.repocleaner.s3.S3FileUploader;
import com.repocleaner.s3.S3Info;
import com.repocleaner.util.CleanResult;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class ZipFileSource implements Source {
    private final String location;
    private final ZipResponseType zipResponseType;

    @Override
    public void saveSource(File sourceFolder) throws RepoCleanerException {
        File zipped = new File(location);
        ZipUtil.extract(zipped, sourceFolder);
        GitUtil.init(sourceFolder);
    }

    @Override
    public void sendCleaned(File sourceFolder, CleanResult cleanResult, File tempFile) throws RepoCleanerException {
        zipResponseType.saveTo(sourceFolder, tempFile);
        S3FileUploader.upload(S3Info.HELD_BUCKET, location, tempFile);
    }
}
