package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HostedAccount {
    private final HostedKey hostedKey;
    private final String repoRegex;
}
