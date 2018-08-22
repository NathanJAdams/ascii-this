package com.repocleaner.getuserconfig.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserConfigRequest {
    private final String jwt;
}
