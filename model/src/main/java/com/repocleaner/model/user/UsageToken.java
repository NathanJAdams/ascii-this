package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsageToken {
    private final String token;
    private final boolean isUsable;
    private final String expiryDateTimeHex;
}
