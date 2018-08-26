package com.repocleaner.lambdagetuserconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetUserConfigLambdaRequest {
    private final String jwt;
}
