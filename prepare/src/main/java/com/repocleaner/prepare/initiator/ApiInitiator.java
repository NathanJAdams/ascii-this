package com.repocleaner.prepare.initiator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiInitiator implements Initiator {
    private final int tokens;

    @Override
    public boolean isPossibleToClean() {
        return true;
    }
}

