package com.repocleaner.lambdaprepare;

import com.repocleaner.config.Config;
import com.repocleaner.initiator.Initiator;
import com.repocleaner.source.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PrepareLambdaRequest {
    private final Initiator initiator;
    private final Config config;
    private final Source source;
}
