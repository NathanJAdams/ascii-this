package com.repocleaner.model;

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

    public boolean isValid() {
        System.out.println("is valid user: " + this);
        if (!active) {
            System.out.println("User is not active");
            return false;
        }
        if (credits <= 0) {
            System.out.println("User has no credits");
            return false;
        }
        return true;
    }
}
