package com.repocleaner.initiator;

public class WebsiteInitiator implements Initiator {
    @Override
    public int getCredits() {
        return 1000000000;
    }

    @Override
    public boolean isPossibleToClean() {
        return true;
    }

    @Override
    public boolean requiresApiResponse() {
        return false;
    }

    @Override
    public void notifyAvailable(String key) {
    }
}
