package users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CleanedRepo {
    private final String masterBranch;
    private final Config config;

    public CleanedRepo(Config config) {
        this("master", config);
    }
}
