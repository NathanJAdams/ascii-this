package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HostedAccount {
    private final String repoRegex;
    private final String personalAccessToken;
}
