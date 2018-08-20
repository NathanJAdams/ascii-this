package com.repocleaner.prepare.rest;

import com.repocleaner.prepare.initiator.Initiator;
import com.repocleaner.sourceinfo.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LambdaRequest {
    private final Initiator initiator;
    private final Source source;
}
