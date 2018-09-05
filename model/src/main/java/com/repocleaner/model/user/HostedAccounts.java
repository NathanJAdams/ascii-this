package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class HostedAccounts {
    private final Map<String, HostedAccount> userNameHostedAccount;
}
