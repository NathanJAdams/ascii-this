package com.repocleaner.clean.rest;

import com.repocleaner.sourceinfo.Source;
import com.repocleaner.userinfo.config.UserConfig;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LambdaRequest {
    private final UserConfig config;
    private final Source<?> source;
}
