package com.repocleaner.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetUserConfigLambdaRequest {
    private final String jwt;
}
