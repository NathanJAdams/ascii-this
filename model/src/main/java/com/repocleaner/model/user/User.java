package com.repocleaner.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private boolean active;
    private int credits;
    private int maxCreditsPerClean;
    private Map<String, HostedAccount> accounts;
    private Map<String, UsageToken> usageTokens;
}
