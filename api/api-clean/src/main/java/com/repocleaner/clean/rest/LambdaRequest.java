package com.repocleaner.clean.rest;

import com.repocleaner.model.config.Config;
import com.repocleaner.model.source.Source;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LambdaRequest {
    private final Config config;
    private final Source source;
}
