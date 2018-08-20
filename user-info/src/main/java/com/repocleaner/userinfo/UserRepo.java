package com.repocleaner.userinfo;

import com.repocleaner.userinfo.config.UserConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UserRepo {
    private UserConfig config;
    private String token;
    private String repoRegex;
    private Map<String, CleanedRepo> cleanedRepos;
}
