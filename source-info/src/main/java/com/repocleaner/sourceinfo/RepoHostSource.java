package com.repocleaner.sourceinfo;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.repohosts.RepoHost;
import com.repocleaner.repohosts.RepoHosts;
import com.repocleaner.sinkinfo.RepoHostSink;
import com.repocleaner.userdb.DatabaseReferenceCreator;
import com.repocleaner.userdb.DbSelector;
import com.repocleaner.userdb.KeyConverter;
import com.repocleaner.userinfo.CleanedRepo;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class RepoHostSource implements Source<RepoHostSink> {
    private final String email;
    private final String host;
    private final String user;
    private final String repo;

    public RepoHostSink saveSourceGetSinkDetails(File sourceFolder) throws RepoCleanerException {
        String encodedEmail = KeyConverter.toKey(email);
        DatabaseReference userRepoRef = DatabaseReferenceCreator.USERS_REF.child(encodedEmail).child(host).child(user);
        CleanedRepo cleanedRepo = new DbSelector<>(userRepoRef, CleanedRepo.class, repo, false).get();
        String branch = cleanedRepo.getMasterBranch();
        RepoHost repoHost = RepoHosts.DEFAULT_INSTANCE.get(host);
        String uri = repoHost.createUrlRepo(user, repo);
        GitUtil.clone(uri, branch, sourceFolder);
        return new RepoHostSink(encodedEmail, host, user, repo, branch);
    }
}
