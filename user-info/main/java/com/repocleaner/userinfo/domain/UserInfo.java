package com.repocleaner.userinfo.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfo {
    private int tokens;
    private List<TokenTransaction> transactions;
    private List<HostedRepo> hostedRepos;
    private Config config;
}
