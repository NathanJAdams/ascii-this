package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HostedToken {
    private final HostedKey hostedKey;
    private final String name;
    private final String token;
    private final boolean isUsable;
    private final String expiryDateTimeHex;
}
