package com.repocleaner.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse {
    private final String key;
    private final String token;
}
