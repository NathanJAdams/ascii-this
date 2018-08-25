package com.repocleaner.transaction;

import java.time.LocalDateTime;

public class BoughtCreditsTransaction extends CreditsTransaction {
    private String currency;
    private LocalDateTime dateTime;
}
