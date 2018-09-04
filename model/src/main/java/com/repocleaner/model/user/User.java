package com.repocleaner.model.user;

import com.repocleaner.model.config.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private final String email;
    private final Config config;
    private final int credits;
}
