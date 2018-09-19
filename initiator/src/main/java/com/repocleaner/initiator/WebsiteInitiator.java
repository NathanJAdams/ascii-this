package com.repocleaner.initiator;

import com.repocleaner.model.Initiator;

public class WebsiteInitiator implements Initiator {
    @Override
    public long getMaxCredits() {
        return 999999999999999999L;
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
