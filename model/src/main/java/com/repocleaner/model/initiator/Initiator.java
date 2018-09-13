package com.repocleaner.model.initiator;

public interface Initiator {
    long getMaxCredits();

    boolean isPossibleToClean();

    boolean requiresApiResponse();

    String getNotificationEndpoint();
}
