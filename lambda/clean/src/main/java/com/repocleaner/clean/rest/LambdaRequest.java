package com.repocleaner.clean.rest;

import com.repocleaner.source.Source;
import com.repocleaner.config.Config;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LambdaRequest {
    private final Config config;
    private final Source source;
}
