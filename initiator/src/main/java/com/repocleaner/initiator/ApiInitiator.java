package com.repocleaner.initiator;

import com.repocleaner.model.initiator.Initiator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiInitiator implements Initiator {
    private final long maxCredits;
    private final String notificationEndpoint;

    @Override
    public boolean isPossibleToClean() {
        return true;
    }

    @Override
    public boolean requiresApiResponse() {
        return true;
    }

    @Override
    public String getNotificationEndpoint() {
        return notificationEndpoint;
    }
}
