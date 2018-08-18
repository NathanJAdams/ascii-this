package clean.source;

import org.eclipse.jgit.api.Git;
import clean.host.RepoHost;
import clean.host.RepoHosts;
import clean.util.GitUtil;
import clean.util.RepoCleanerException;

import java.io.File;

public class WebsiteSource implements Source {
    private static final RepoHosts REPO_HOSTS = new RepoHosts();

    private final String host;
    private final String user;
    private final String repo;
    private final String branch;

    public WebsiteSource(String host, String user, String repo, String branch) {
        this.host = host;
        this.user = user;
        this.repo = repo;
        this.branch = branch;
    }

    public Git saveCodeTo(File folder) throws RepoCleanerException {
        RepoHost repoHost = REPO_HOSTS.get(host);
        String uri = repoHost.createUrlRepo(user, repo);
        return GitUtil.clone(uri, branch, folder);
    }

    @Override
    public String createCleanBranchName(Git git) throws RepoCleanerException {
        return "repo-cleaner";
    }

    @Override
    public void sendCleanedCode(Git git, String cleanedBranch, String title, String description) throws RepoCleanerException {
        ZipResponseType.Diff.sendCleanedCode(git);
    }
}
