package com.repocleaner.model.initiator;

public interface Initiator {
    long getCredits();

    boolean isPossibleToClean();

    boolean requiresApiResponse();

    String getNotificationEndpoint();
}
