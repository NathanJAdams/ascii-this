package com.repocleaner.io;

import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.source.Source;
import com.repocleaner.model.user.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PrepareLambdaRequest {
    private final Initiator initiator;
    private final Source source;
    private final Config config;
}
