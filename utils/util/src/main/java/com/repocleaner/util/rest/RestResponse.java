package com.repocleaner.util.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestResponse<T> {
    private final int status;
    private final T response;
}
