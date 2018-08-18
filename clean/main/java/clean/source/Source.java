package clean.source;

import org.eclipse.jgit.api.Git;
import clean.util.RepoCleanerException;

import java.io.File;

public interface Source {
    Git saveCodeTo(File folder) throws RepoCleanerException;

    String createCleanBranchName(Git git) throws RepoCleanerException;

    void sendCleanedCode(Git git, String cleanedBranch, String title, String description) throws RepoCleanerException;
}
