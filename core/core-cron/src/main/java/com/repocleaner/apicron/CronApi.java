package com.repocleaner.apicron;

import com.repocleaner.initiator.CronInitiator;
import com.repocleaner.initiator.InitiatorGsonCustomiser;
import com.repocleaner.io.external.CronIO;
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
import com.repocleaner.sink.SinkGsonCustomiser;
import com.repocleaner.source.RepoHostSource;
import com.repocleaner.source.SourceGsonCustomiser;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class CronApi {
    private static final JsonUtil JSON_UTIL = new JsonUtil(new InitiatorGsonCustomiser(), new SinkGsonCustomiser(), new SourceGsonCustomiser());

    public static void startCleanJobs(UserIO userIO, CronIO cronIO) {
        try {
            Map<String, HostedRepo> hostedRepos = userIO.getHostedReposToClean(LocalDateTime.now(), 100);
            hostedRepos.values()
                    .forEach(hostedRepo -> startCleanJob(userIO, cronIO, hostedRepo));
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
    }

    private static void startCleanJob(UserIO userIO, CronIO cronIO, HostedRepo hostedRepo) {
        try {
            LifecycleRequest lifecycleRequest = createLifecycleRequest(userIO, hostedRepo);
            cronIO.waiting(lifecycleRequest, JSON_UTIL);
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
        String encryptedToken = hostedAccount.getEncryptedToken();
        Source source = createSource(hostedAccount, hostedRepo);
        Sink sink = createSink(hostedAccount, hostedRepo);
        Config config = hostedRepo.getConfig();
        return new LifecycleRequest(id, encryptedToken, initiator, config, source, sink);
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
