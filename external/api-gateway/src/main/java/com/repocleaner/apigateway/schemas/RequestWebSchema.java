package com.repocleaner.apigateway.schemas;

import com.repocleaner.apigateway.Schema;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestWebSchema implements Schema {
    private final String provider;
    private final String user;
    private final String repo;
    private final String branch;
}
