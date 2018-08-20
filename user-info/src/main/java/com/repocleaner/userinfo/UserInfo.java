package com.repocleaner.userinfo;

import com.repocleaner.userinfo.config.UserConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UserInfo {
    private long tokens;
    private TransactionInfo transactionInfo;
    private String hostRegex;
    private Map<String, HostedRepo> hostedRepos;
    private UserConfig config;
}
