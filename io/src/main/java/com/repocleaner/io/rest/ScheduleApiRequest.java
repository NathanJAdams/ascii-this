package com.repocleaner.io.rest;

import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScheduleApiRequest {
    private final String userId;
    private final String token;
    private final Source source;
    private final Sink sink;
}
