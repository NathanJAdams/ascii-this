package com.repocleaner.prepare.initiator;

public class WebsiteInitiator implements Initiator {
    @Override
    public int getTokens() {
        return 1000000000;
    }

    @Override
    public boolean isPossibleToClean() {
        return true;
    }
}
