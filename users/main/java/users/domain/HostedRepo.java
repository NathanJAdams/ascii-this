package users.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HostedRepo {
    private final String repoHost;
    private final String user;
    private final boolean isPaused;
    private final Config config;
    private final String cleanedRepoRegex;
    private final List<CleanedRepo> cleanedRepos;
}
