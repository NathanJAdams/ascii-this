package com.repocleaner.model;

public interface CreditsTransaction {
    String getUserEmail();

    TransactionType getTransactionType();

    long getCredits();
}
