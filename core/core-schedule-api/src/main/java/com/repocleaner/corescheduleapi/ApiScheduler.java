package com.repocleaner.corescheduleapi;

import com.repocleaner.initiator.ApiInitiator;
import com.repocleaner.io.external.ScheduleIO;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.model.Config;
import com.repocleaner.model.Initiator;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import com.repocleaner.model.UsageToken;
import com.repocleaner.model.User;
import com.repocleaner.util.LocalDateTimeUtil;
import com.repocleaner.util.RepoCleanerException;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApiScheduler {
    public static String schedule(UserIO userIO, ScheduleIO scheduleIO, String userId, String token, Source source, Sink sink) throws RepoCleanerException {
        User user = getUser(userIO, userId);
        UsageToken usageToken = getUsageToken(user, token);
        String id = UUID.randomUUID().toString();
        Initiator initiator = createInitiator(user, usageToken);
        Config config = usageToken.getConfig();
        LifecycleRequest lifecycleRequest = new LifecycleRequest(id, initiator, config, source, sink);
        scheduleIO.schedule(lifecycleRequest);
        return id;
    }

    private static User getUser(UserIO userIO, String userId) throws RepoCleanerException {
        User user = userIO.getUser(userId);
        if (user == null) {
            throw new RepoCleanerException("User id is not valid");
        }
        return user;
    }

    private static UsageToken getUsageToken(User user, String token) throws RepoCleanerException {
        UsageToken usageToken = user.getUsageTokens().get(token);
        if (usageToken == null) {
            throw new RepoCleanerException("Token is not valid");
        }
        String expiryTimeHex = usageToken.getExpiryTimeHex();
        LocalDateTime expiryTime = LocalDateTimeUtil.toLocalDateTime(expiryTimeHex);
        if (expiryTime.isBefore(LocalDateTime.now())) {
            throw new RepoCleanerException("Token is expired");
        }
        return usageToken;
    }

    private static Initiator createInitiator(User user, UsageToken usageToken) {
        int maxCredits = Math.min(user.getMaxCreditsPerClean(), user.getCredits());
        String notificationEndPoint = usageToken.getNotificationEndPoint();
        return new ApiInitiator(maxCredits, notificationEndPoint);
    }
}
