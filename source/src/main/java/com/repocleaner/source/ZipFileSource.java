package com.repocleaner.source;

import com.repocleaner.model.Source;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class ZipFileSource implements Source {
    private final String location;

    @Override
    public void download(File sourceFolder) throws RepoCleanerException {
        File zipped = new File(location);
        ZipUtil.extract(zipped, sourceFolder);
        GitUtil.init(sourceFolder);
    }
}
