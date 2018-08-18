package users.domain;

import lombok.Getter;

@Getter
public class CleanedTokenTransaction extends TokenTransaction {
    private final String commitLink;

    public CleanedTokenTransaction(int tokens, String commitLink) {
        super(TransactionType.Debit, tokens);
        this.commitLink = commitLink;
    }
}
