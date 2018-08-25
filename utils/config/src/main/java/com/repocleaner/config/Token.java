package com.repocleaner.config;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Token {
    private String host;
    private String user;
    private String token;
    private String repoRegex;
    private Map<String, String> repoMasterBranches;
}
