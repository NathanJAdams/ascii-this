package users.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CleanedRepo {
    private String masterBranch;
    private Config config;
}
