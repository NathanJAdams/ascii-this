package com.repocleaner.apigateway.schemas;

import com.repocleaner.apigateway.Schema;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenSchema implements Schema {
    private final String token;
}
