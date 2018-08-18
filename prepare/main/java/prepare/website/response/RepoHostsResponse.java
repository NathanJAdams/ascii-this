package clean.website.response;

import clean.host.RepoHost;

import java.util.List;

public class RepoHostsResponse {
    private final List<RepoHost> repoHosts;

    public RepoHostsResponse(List<RepoHost> repoHosts) {
        this.repoHosts = repoHosts;
    }

    public List<RepoHost> getRepoHosts() {
        return repoHosts;
    }
}
