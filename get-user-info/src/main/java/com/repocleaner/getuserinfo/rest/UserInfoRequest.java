package com.repocleaner.getuserinfo.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoRequest {
    private final String jwt;
}
