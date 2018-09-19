package com.repocleaner.transaction;

import com.repocleaner.model.CreditsTransaction;
import com.repocleaner.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UsedCreditsTransaction implements CreditsTransaction {
    private final String userEmail;
    private final TransactionType transactionType;
    private final long credits;
    private final String commitLink;
}
