package com.repocleaner.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HostedAccountConfig {
    private Config config;
    private String host;
    private String user;
    private String token;
    private String repoRegex;
    private Map<String, RepoConfig> repoConfigs;
}
