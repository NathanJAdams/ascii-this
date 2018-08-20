package com.repocleaner.prepare.initiator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfigInitiator implements Initiator {
    private final int tokens;

    @Override
    public boolean isPossibleToClean() {
        // TODO return NOT (does the repo-cleaner branch exist and is it mergeable and has not been committed)
        return false;
    }
}

