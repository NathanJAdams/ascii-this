package com.repocleaner.userinfo;

import com.repocleaner.userinfo.config.UserConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleanedRepo {
    private String masterBranch;
    private UserConfig config;
}
