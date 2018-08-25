package com.repocleaner.initiator;

public interface Initiator {
    int getCredits();

    boolean isPossibleToClean();

    boolean requiresApiResponse();

    void notifyAvailable(String key);
}
