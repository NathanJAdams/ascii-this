package com.repocleaner.user;

import com.repocleaner.config.Config;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class User {
    private Config config;
    private String host;
    private String user;
    private String token;
    private String repoRegex;
    private Map<String, String> repoMasterBranches;
}
