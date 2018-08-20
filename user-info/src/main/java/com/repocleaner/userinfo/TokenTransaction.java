package com.repocleaner.userinfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenTransaction {
    private TransactionType transactionType;
    private int tokens;
}
