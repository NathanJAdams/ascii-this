package com.repocleaner.initiator;

public interface Initiator {
    int getTokens();

    boolean isPossibleToClean();

    boolean requiresApiResponse();

    void notifyAvailable(String key);
}
