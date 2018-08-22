package com.repocleaner.initiator;

import com.repocleaner.util.rest.RestRequest;
import com.repocleaner.util.rest.RestUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiInitiator implements Initiator {
    private final int tokens;
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
    public void notifyAvailable(String key) {
        if (notificationEndpoint != null) {
            CleanedNotification notification = new CleanedNotification(key);
            RestRequest<Void> request = RestRequest.POST(notificationEndpoint, notification, Void.class);
            RestUtil.getResponse(request);
        }
    }
}
