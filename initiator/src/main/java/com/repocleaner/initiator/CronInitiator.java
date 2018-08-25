package com.repocleaner.initiator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CronInitiator implements Initiator {
    private final int credits;

    @Override
    public boolean isPossibleToClean() {
        // TODO return NOT (does the repo-cleaner branch exist and is it mergeable and has not been committed)
        return false;
    }

    @Override
    public boolean requiresApiResponse() {
        return false;
    }

    @Override
    public void notifyAvailable(String key) {
    }
}
