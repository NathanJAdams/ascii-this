package com.repocleaner.model.initiator;

public interface Initiator {
    default boolean isValid() {
        return (getMaxCredits() > 0) && isPossibleToClean();
    }

    long getMaxCredits();

    boolean isPossibleToClean();

    boolean requiresApiResponse();

    String getNotificationEndpoint();
}
