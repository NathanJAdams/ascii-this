package com.repocleaner.model.user;

import com.repocleaner.model.config.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class User {
    private final String email;
    private final Config config;
    private final int credits;
    private final Map<String, HostedAccounts> hostedAccounts;
    private final Map<String, UsageToken> namedUsageTokens;
}
