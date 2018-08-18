package clean.source;

import org.eclipse.jgit.api.Git;
import clean.util.GitUtil;
import clean.util.RepoCleanerException;
import clean.util.ZipUtil;

import java.io.File;

// TODO
public enum ZipResponseType {
    Zip {
        @Override
        public void sendCleanedCode(Git git) throws RepoCleanerException {
            File folder = git.getRepository().getDirectory();
            File zippedFile = new File(folder, "cleaned.zip");
            ZipUtil.zip(folder, zippedFile);
        }
    },
    Diff {
        @Override
        public void sendCleanedCode(Git git) throws RepoCleanerException {
            String diff = GitUtil.getLatestDiff(git);

        }
    };

    public abstract void sendCleanedCode(Git git) throws RepoCleanerException;
}
