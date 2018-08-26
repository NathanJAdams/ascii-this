package com.repocleaner.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class HostedAccount {
    private final Map<String, Boolean> usageTokens;
    private final String repoRegex;
    private final Map<String, String> repoMasterBranches;
}
