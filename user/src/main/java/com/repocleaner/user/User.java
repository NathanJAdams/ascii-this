package com.repocleaner.user;

import com.repocleaner.config.Config;
import com.repocleaner.transaction.CreditsTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public class User {
    private final int credits;
    private final String email;
    private final Config config;
    private final List<CreditsTransaction> transactions;
    private final Map<String, HostedAccount> hostedAccounts;
}
