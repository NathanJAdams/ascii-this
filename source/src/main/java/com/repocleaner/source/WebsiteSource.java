package com.repocleaner.source;

import com.repocleaner.repohosts.RepoHost;
import com.repocleaner.repohosts.RepoHosts;
import com.repocleaner.sink.WebResponseType;
import com.repocleaner.sink.WebSink;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class WebsiteSource implements Source<WebSink> {
    private final String endpoint;
    private final WebResponseType webResponseType;
    private final String host;
    private final String user;
    private final String repo;
    private final String branch;

    @Override
    public WebSink saveSourceGetSink(File sourceFolder) throws RepoCleanerException {
        RepoHost repoHost = RepoHosts.DEFAULT_INSTANCE.get(host);
        String uri = repoHost.createUrlRepo(user, repo);
        GitUtil.clone(uri, branch, sourceFolder);
        return new WebSink(endpoint, webResponseType);
    }
}
