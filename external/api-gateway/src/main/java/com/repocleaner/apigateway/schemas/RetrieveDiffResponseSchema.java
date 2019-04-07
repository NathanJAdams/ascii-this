package com.repocleaner.apigateway.schemas;

import com.repocleaner.apigateway.Schema;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RetrieveDiffResponseSchema implements Schema {
    private final DiffSchema diff;
}
