package com.repocleaner.sinkinfo;

import com.google.firebase.database.DatabaseReference;
import com.repocleaner.repohosts.RepoHost;
import com.repocleaner.repohosts.RepoHosts;
import com.repocleaner.userdb.DatabaseReferenceCreator;
import com.repocleaner.userdb.DbSelector;
import com.repocleaner.userdb.KeyConverter;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.jgit.api.Git;

import java.io.File;

@AllArgsConstructor
@Getter
public class RepoHostSink implements Sink {
    private final String email;
    private final String host;
    private final String user;
    private final String repo;
    private final String branch;

    @Override
    public void sendSource(File sourceFolder, File tempFile) throws RepoCleanerException {
        RepoHost repoHost = RepoHosts.DEFAULT_INSTANCE.get(host);
        DatabaseReference userRepoRef = getUserRepoRef();
        String token = getToken(userRepoRef);
        String title = "Cleaned by repocleaner.com";
        String description = "Cleaned by repocleaner.com";
        String masterBranch = getMasterBranch(userRepoRef);
        String cleanedBranch = getBranchName(sourceFolder);
        repoHost.raisePullRequest(user, token, repo, title, description, masterBranch, cleanedBranch);
    }

    private DatabaseReference getUserRepoRef() {
        String encodedEmail = KeyConverter.toKey(email);
        return DatabaseReferenceCreator.USERS_REF.child(encodedEmail).child(host).child(user);
    }

    private String getToken(DatabaseReference userRepoRef) {
        return new DbSelector<>(userRepoRef, String.class, "token", false).get();
    }

    private String getMasterBranch(DatabaseReference userRepoRef) {
        DatabaseReference cleanedRepoRef = userRepoRef.child(repo);
        return new DbSelector<>(cleanedRepoRef, String.class, "masterBranch", false).get();
    }

    private String getBranchName(File sourceFolder) throws RepoCleanerException {
        Git git = GitUtil.init(sourceFolder);
        return GitUtil.getBranchName(git);
    }
}
