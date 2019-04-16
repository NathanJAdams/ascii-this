package com.repocleaner.lambdarequestcron;

import com.repocleaner.core.initiators.CronInitiator;
import com.repocleaner.core.io.RequestIO;
import com.repocleaner.core.io.UserIO;
import com.repocleaner.core.model.CleanRequest;
import com.repocleaner.core.model.Config;
import com.repocleaner.core.model.HostedAccount;
import com.repocleaner.core.model.HostedRepo;
import com.repocleaner.core.model.Initiator;
import com.repocleaner.core.model.Sink;
import com.repocleaner.core.model.Source;
import com.repocleaner.core.model.User;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.sink.RepoHostSink;
import com.repocleaner.source.RepoHostSource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class CronRequester {
    public void schedule(UserIO userIO, RequestIO requestIO) throws RepoCleanerException {
        Map<String, HostedRepo> hostedRepos = userIO.getHostedReposToClean(LocalDateTime.now(), 100);
        hostedRepos.values()
                .forEach(hostedRepo -> startCleanJob(userIO, requestIO, hostedRepo));
    }

    private void startCleanJob(UserIO userIO, RequestIO requestIO, HostedRepo hostedRepo) {
        try {
            CleanRequest cleanRequest = createLifecycleRequest(userIO, hostedRepo);
            requestIO.request(cleanRequest);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
    }

    private CleanRequest createLifecycleRequest(UserIO userIO, HostedRepo hostedRepo) throws RepoCleanerException {
        String userId = hostedRepo.getUserId();
        User user = userIO.getUser(userId);
        if (!user.isValid()) {
            throw new RepoCleanerException("invalid user");
        }
        Initiator initiator = createInitiator(user);
        if (!initiator.isValid()) {
            throw new RepoCleanerException("invalid initiator");
        }
        String id = UUID.randomUUID().toString();
        HostedAccount hostedAccount = user.getAccounts().get(hostedRepo.getAccountId());
        Source source = createSource(hostedAccount, hostedRepo);
        Sink sink = createSink(hostedAccount, hostedRepo);
        Config config = hostedRepo.getConfig();
        return new CleanRequest(id, initiator, config, source, sink);
    }

    private Initiator createInitiator(User user) {
        long credits = user.getCredits();
        long maxCreditsPerClean = user.getMaxCreditsPerClean();
        long maxCredits = Math.min(credits, maxCreditsPerClean);
        return new CronInitiator(maxCredits);
    }

    private Source createSource(HostedAccount hostedAccount, HostedRepo hostedRepo) {
        String host = hostedAccount.getHost();
        String userName = hostedAccount.getUserName();
        String repoName = hostedRepo.getRepoName();
        String masterBranch = hostedRepo.getMasterBranch();
        return new RepoHostSource(host, userName, repoName, masterBranch);
    }

    private Sink createSink(HostedAccount hostedAccount, HostedRepo hostedRepo) {
        String host = hostedAccount.getHost();
        String userName = hostedAccount.getUserName();
        String repoName = hostedRepo.getRepoName();
        String masterBranch = hostedRepo.getMasterBranch();
        String personalAccessToken = hostedAccount.getEncryptedToken();
        return new RepoHostSink(host, userName, repoName, masterBranch, personalAccessToken);
    }
}
