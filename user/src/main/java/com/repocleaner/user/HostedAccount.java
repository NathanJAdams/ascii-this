package com.repocleaner.user;

import java.util.List;
import java.util.Map;

public class HostedAccount {
    private String host;
    private String account;
    private List<String> usageTokens;
    private String repoRegex;
    private Map<String, String> repoMasterBranches;
}
