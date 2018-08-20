package com.repocleaner.userinfo;

import com.repocleaner.userinfo.config.UserConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HostedRepo {
    private UserConfig config;
    private String userRegex;
    private Map<String, UserRepo> userRepos;
}
