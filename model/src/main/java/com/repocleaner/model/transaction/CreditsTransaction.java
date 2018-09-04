package com.repocleaner.model.transaction;

public interface CreditsTransaction {
    String getUserEmail();

    TransactionType getTransactionType();

    long getCredits();
}
