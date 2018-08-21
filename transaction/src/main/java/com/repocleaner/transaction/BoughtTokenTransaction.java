package com.repocleaner.transaction;

import java.time.LocalDateTime;

public class BoughtTokenTransaction extends TokenTransaction {
    private String currency;
    private LocalDateTime dateTime;
}
