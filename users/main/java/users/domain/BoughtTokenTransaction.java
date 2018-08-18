package users.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoughtTokenTransaction extends TokenTransaction {
    private final String currency;
    private final LocalDateTime dateTime;

    public BoughtTokenTransaction(int tokens, String currency, LocalDateTime dateTime) {
        super(TransactionType.Credit, tokens);
        this.currency = currency;
        this.dateTime = dateTime;
    }
}
