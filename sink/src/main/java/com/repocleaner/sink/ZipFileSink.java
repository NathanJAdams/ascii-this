package com.repocleaner.sink;

import com.repocleaner.model.Sink;
import com.repocleaner.s3.S3FileActions;
import com.repocleaner.s3.S3Info;
import com.repocleaner.util.CleanResult;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class ZipFileSink implements Sink {
    private final String location;
    private final ZipResponseType zipResponseType;

    @Override
    public void upload(File sourceFolder, CleanResult cleanResult, File tempFile) throws RepoCleanerException {
        zipResponseType.saveTo(sourceFolder, tempFile);
        S3FileActions.upload(S3Info.HELD_BUCKET, location, tempFile);
    }
}
