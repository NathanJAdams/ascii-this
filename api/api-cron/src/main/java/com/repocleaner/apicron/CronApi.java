package com.repocleaner.apicron;

import com.repocleaner.initiator.CronInitiator;
import com.repocleaner.initiator.InitiatorGsonCustomiser;
import com.repocleaner.io.external.CronIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.receive.LifecycleRequest;
import com.repocleaner.model.source.Source;
import com.repocleaner.model.user.Config;
import com.repocleaner.model.user.HostedAccount;
import com.repocleaner.model.user.HostedRepo;
import com.repocleaner.model.user.User;
import com.repocleaner.source.RepoHostSource;
import com.repocleaner.source.SourceGsonCustomiser;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class CronApi {
    private static final JsonUtil JSON_UTIL = new JsonUtil(new InitiatorGsonCustomiser(), new SourceGsonCustomiser());

    public static void startCleanJobs(UserIO userIO, CronIO cronIO) {
        Map<String, HostedRepo> hostedRepos = userIO.getHostedReposToClean(LocalDateTime.now(), 100);
        hostedRepos.values()
                .forEach(hostedRepo -> startCleanJob(userIO, cronIO, hostedRepo));
    }

    private static void startCleanJob(UserIO userIO, CronIO cronIO, HostedRepo hostedRepo) {
        LifecycleRequest lifecycleRequest = createLifecycleRequest(userIO, hostedRepo);
        try {
            cronIO.waiting(lifecycleRequest, JSON_UTIL);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
    }

    private static LifecycleRequest createLifecycleRequest(UserIO userIO, HostedRepo hostedRepo) {
        String userId = hostedRepo.getUserId();
        User user = userIO.getUser(userId);
        if (!user.isValid()) {
            return null;
        }
        Initiator initiator = createInitiator(user);
        if (!initiator.isValid()) {
            return null;
        }
        String id = UUID.randomUUID().toString();
        String token = null;
        String accountId = hostedRepo.getAccountId();
        HostedAccount hostedAccount = user.getAccounts().get(accountId);
        Source source = createSource(hostedAccount, hostedRepo);
        Config config = hostedRepo.getConfig();
        return new LifecycleRequest(id, token, initiator, config, source);
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
        String personalAccessToken = hostedAccount.getPersonalAccessToken();
        return new RepoHostSource(host, userName, repoName, masterBranch, personalAccessToken);
    }
}
