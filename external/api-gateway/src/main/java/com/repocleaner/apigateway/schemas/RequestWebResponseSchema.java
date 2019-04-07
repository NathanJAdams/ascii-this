package com.repocleaner.apigateway.schemas;

import com.repocleaner.apigateway.Schema;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestWebResponseSchema implements Schema {
    private final String id;
}
