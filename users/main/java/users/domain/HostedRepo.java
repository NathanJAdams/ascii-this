package users.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HostedRepo {
    private String host;
    private String user;
    private boolean isPaused;
    private Config config;
    private String cleanedRepoRegex;
    private List<CleanedRepo> cleanedRepos;
}
