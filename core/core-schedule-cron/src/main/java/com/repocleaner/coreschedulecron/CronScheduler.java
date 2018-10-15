package com.repocleaner.coreschedulecron;

import com.repocleaner.initiator.CronInitiator;
import com.repocleaner.io.external.ScheduleIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.model.Config;
import com.repocleaner.model.HostedAccount;
import com.repocleaner.model.HostedRepo;
import com.repocleaner.model.Initiator;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import com.repocleaner.model.User;
import com.repocleaner.sink.RepoHostSink;
import com.repocleaner.source.RepoHostSource;
import com.repocleaner.util.RepoCleanerException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class CronScheduler {
    public static void schedule(UserIO userIO, ScheduleIO scheduleIO) throws RepoCleanerException {
        Map<String, HostedRepo> hostedRepos = userIO.getHostedReposToClean(LocalDateTime.now(), 100);
        hostedRepos.values()
                .forEach(hostedRepo -> startCleanJob(userIO, scheduleIO, hostedRepo));
    }

    private static void startCleanJob(UserIO userIO, ScheduleIO scheduleIO, HostedRepo hostedRepo) {
        try {
            LifecycleRequest lifecycleRequest = createLifecycleRequest(userIO, hostedRepo);
            scheduleIO.schedule(lifecycleRequest);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
    }

    private static LifecycleRequest createLifecycleRequest(UserIO userIO, HostedRepo hostedRepo) throws RepoCleanerException {
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
        return new LifecycleRequest(id, initiator, config, source, sink);
    }

    private static Initiator createInitiator(User user) {
        long credits = user.getCredits();
        long maxCreditsPerClean = user.getMaxCreditsPerClean();
        long maxCredits = Math.min(credits, maxCreditsPerClean);
        return new CronInitiator(maxCredits);
    }

    private static Source createSource(HostedAccount hostedAccount, HostedRepo hostedRepo) {
        String host = hostedAccount.getHost();
        String userName = hostedAccount.getUserName();
        String repoName = hostedRepo.getRepoName();
        String masterBranch = hostedRepo.getMasterBranch();
        return new RepoHostSource(host, userName, repoName, masterBranch);
    }

    private static Sink createSink(HostedAccount hostedAccount, HostedRepo hostedRepo) {
        String host = hostedAccount.getHost();
        String userName = hostedAccount.getUserName();
        String repoName = hostedRepo.getRepoName();
        String masterBranch = hostedRepo.getMasterBranch();
        String personalAccessToken = hostedAccount.getEncryptedToken();
        return new RepoHostSink(host, userName, repoName, masterBranch, personalAccessToken);
    }
}
