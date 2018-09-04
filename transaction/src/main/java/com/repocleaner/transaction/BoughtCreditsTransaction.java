package com.repocleaner.transaction;

import com.repocleaner.model.transaction.CreditsTransaction;
import com.repocleaner.model.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class BoughtCreditsTransaction implements CreditsTransaction {
    private final String userEmail;
    private final TransactionType transactionType;
    private final long credits;
    private final String currency;
    private final LocalDateTime dateTime;
}
