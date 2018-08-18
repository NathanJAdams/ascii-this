package clean.source;

import org.eclipse.jgit.api.Git;
import clean.util.GitUtil;
import clean.util.RepoCleanerException;
import clean.util.ZipUtil;

import java.io.File;

public class ZipSource implements Source {
    private final String jarLocation;
    private final ZipResponseType responseType;

    public ZipSource(String jarLocation, ZipResponseType responseType) {
        this.jarLocation = jarLocation;
        this.responseType = responseType;
    }

    @Override
    public Git saveCodeTo(File folder) throws RepoCleanerException {
        File zipped = new File(jarLocation);
        ZipUtil.extract(zipped, folder);
        return GitUtil.init(folder);
    }

    @Override
    public String createCleanBranchName(Git git) throws RepoCleanerException {
        return "repo-cleaner";
    }

    @Override
    public void sendCleanedCode(Git git, String cleanedBranch, String title, String description) throws RepoCleanerException {
        responseType.sendCleanedCode(git);
    }
}
