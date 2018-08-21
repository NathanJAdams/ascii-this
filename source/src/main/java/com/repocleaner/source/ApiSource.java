package com.repocleaner.source;

import com.repocleaner.sink.WebResponseType;
import com.repocleaner.sink.WebSink;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class ApiSource implements Source<WebSink> {
    private final String jarLocation;
    private final String endpoint;
    private final WebResponseType webResponseType;

    @Override
    public WebSink saveSourceGetSink(File sourceFolder) throws RepoCleanerException {
        File zipped = new File(jarLocation);
        ZipUtil.extract(zipped, sourceFolder);
        GitUtil.init(sourceFolder);
        return new WebSink(endpoint, webResponseType);
    }
}
