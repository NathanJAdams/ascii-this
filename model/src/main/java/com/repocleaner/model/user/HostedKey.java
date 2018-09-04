package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HostedKey {
    private final String userEmail;
    private final String host;
    private final String userName;
}
