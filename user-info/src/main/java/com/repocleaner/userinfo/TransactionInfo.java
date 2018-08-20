package com.repocleaner.userinfo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionInfo {
    private List<TokenTransaction> transactions;
}
