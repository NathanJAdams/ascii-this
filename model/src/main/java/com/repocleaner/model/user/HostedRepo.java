package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HostedRepo {
    private final HostedKey hostedKey;
    private final String repo;
    private final String masterBranch;
    private final boolean isActive;
    private final String nextCleanDateTimeHex;
}
