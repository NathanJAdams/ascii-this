package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HostedRepo {
    private final String userEmail;
    private final String host;
    private final String userName;
    private final String repo;
    private final String masterBranch;
    private final boolean isActive;
    private final String nextCleanDateTimeHex;
}
