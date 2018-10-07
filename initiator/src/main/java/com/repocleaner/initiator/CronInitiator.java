package com.repocleaner.initiator;

import com.repocleaner.model.Initiator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CronInitiator implements Initiator {
    private final long maxCredits;

    @Override
    public boolean isPossibleToClean() {
        // TODO return NOT (does the repo-cleaner branch exist and is it mergeable and has not been committed)
        return true;
    }

    @Override
    public boolean requiresApiResponse() {
        return false;
    }

    @Override
    public String getNotificationEndpoint() {
        return null;
    }
}
