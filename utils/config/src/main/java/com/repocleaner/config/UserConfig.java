package com.repocleaner.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UserConfig {
    private Config config;
    private Map<String, HostedAccountConfig> hostedRepos;
}
