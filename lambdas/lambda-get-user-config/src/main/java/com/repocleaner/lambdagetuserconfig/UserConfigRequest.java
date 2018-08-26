package com.repocleaner.lambdagetuserconfig;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserConfigRequest {
    private final String jwt;
}
