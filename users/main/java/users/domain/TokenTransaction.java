package users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenTransaction {
    private final TransactionType transactionType;
    private final int tokens;
}
