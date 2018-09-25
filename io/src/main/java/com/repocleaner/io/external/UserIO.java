package com.repocleaner.io.external;

import com.repocleaner.model.HostedRepo;
import com.repocleaner.model.User;

import java.time.LocalDateTime;
import java.util.Map;

public interface UserIO {
    User getUser(String userId);

    Map<String, HostedRepo> getHostedReposToClean(LocalDateTime end, int max);

    boolean setEncodedToken(String userId, String encodedToken);
}
