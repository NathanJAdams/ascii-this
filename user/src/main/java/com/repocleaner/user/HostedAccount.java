package com.repocleaner.user;

import com.repocleaner.token.UsageToken;

import java.util.List;
import java.util.Map;

public class HostedAccount {
    private String host;
    private String account;
    private List<UsageToken> usageTokens;
    private String repoRegex;
    private Map<String, String> repoMasterBranches;
}
