package com.repocleaner.io;

import com.repocleaner.model.HostedRepo;
import com.repocleaner.model.User;
import com.repocleaner.util.RepoCleanerException;

import java.time.LocalDateTime;
import java.util.Map;

public interface UserIO {
    User getUser(String userId) throws RepoCleanerException;

    Map<String, HostedRepo> getHostedReposToClean(LocalDateTime end, int max) throws RepoCleanerException;
}
