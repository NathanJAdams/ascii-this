package com.repocleaner.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepoConfig {
    private String masterBranch;
    private Config config;
}
