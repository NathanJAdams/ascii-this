package users.rest;

import users.domain.Config;
import users.domain.HostedRepo;
import users.domain.TokenTransaction;

import java.util.Collections;
import java.util.List;

public class UserInfoResponse {
    public static final UserInfoResponse EMPTY_RESPONSE = new UserInfoResponse(-Integer.MAX_VALUE, Collections.emptyList(), Collections.emptyList(), Config.EMPTY_CONFIG);

    private final int tokens;
    private final List<TokenTransaction> transactions;
    private final List<HostedRepo> hostedRepos;
    private final Config config;

    public UserInfoResponse(int tokens, List<TokenTransaction> transactions, List<HostedRepo> hostedRepos, Config config) {
        this.tokens = tokens;
        this.transactions = transactions;
        this.hostedRepos = hostedRepos;
        this.config = config;
    }

    public int getTokens() {
        return tokens;
    }

    public List<TokenTransaction> getTransactions() {
        return transactions;
    }

    public List<HostedRepo> getHostedRepos() {
        return hostedRepos;
    }

    public Config getConfig() {
        return config;
    }
}
