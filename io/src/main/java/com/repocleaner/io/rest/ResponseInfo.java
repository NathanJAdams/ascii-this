package com.repocleaner.io.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseInfo {
    private final boolean success;
    private final String message;
}
