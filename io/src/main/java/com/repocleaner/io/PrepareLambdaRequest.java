package com.repocleaner.io;

import com.repocleaner.model.config.Config;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.source.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PrepareLambdaRequest {
    private final Initiator initiator;
    private final Config config;
    private final Source source;
}
