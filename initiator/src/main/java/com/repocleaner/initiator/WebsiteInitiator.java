package com.repocleaner.initiator;

import com.repocleaner.model.initiator.Initiator;

public class WebsiteInitiator implements Initiator {
    @Override
    public long getCredits() {
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
    public String getNotificationEndpoint() {
        return null;
    }
}
