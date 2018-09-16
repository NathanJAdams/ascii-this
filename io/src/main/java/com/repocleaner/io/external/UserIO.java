package com.repocleaner.io.external;

import com.repocleaner.model.user.HostedRepo;
import com.repocleaner.model.user.User;

import java.time.LocalDateTime;
import java.util.Map;

public interface UserIO {
    User getUser(String userId);

    Map<String, HostedRepo> getHostedReposToClean(LocalDateTime end, int max);
}
