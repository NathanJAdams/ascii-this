package com.repocleaner.source;

import com.repocleaner.model.Source;
import com.repocleaner.repohost.RepoHostBase;
import com.repocleaner.repohost.RepoHosts;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class RepoHostSource implements Source {
    private final String host;
    private final String user;
    private final String repo;
    private final String masterBranch;

    @Override
    public void download(File sourceFolder) throws RepoCleanerException {
        RepoHostBase repoHost = RepoHosts.DEFAULT_INSTANCE.get(host);
        String uri = repoHost.createUrlRepo(user, repo);
        GitUtil.clone(uri, masterBranch, sourceFolder);
    }
}
