package com.repocleaner.clean.unused;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import com.repocleaner.clean.util.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FilePathStreamer implements Supplier<Stream<String>> {
    private final String refName;

    public FilePathStreamer() {
        this("head");
    }

    public FilePathStreamer(String refName) {
        this.refName = refName;
    }

    @Override
    public Stream<String> get() {
        return StreamUtil.stream(iterable());
    }

    public Iterable<String> iterable() {
        File branchDir = new File("/tmp");
        Repository repository = getRepository(branchDir);
        if (repository == null) {
            return null;
        }
        Ref ref = getRef(repository, refName);
        if (ref == null) {
            return null;
        }
        ObjectId objectId = ref.getObjectId();
        return iterableFromRepoAndId(repository, objectId);
    }

    private Repository getRepository(File branchDir) {
        File gitDir = new File(branchDir, ".git");
        FileRepositoryBuilder builder = new FileRepositoryBuilder()
                .setGitDir(gitDir)
                .readEnvironment()
                .findGitDir();
        try {
            return builder.build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Ref getRef(Repository repository, String ref) {
        try {
            return repository.findRef(ref);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private RevCommit getParseCommit(RevWalk walk, ObjectId objectId) {
        try {
            return walk.parseCommit(objectId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Iterable<String> iterableFromRepoAndId(Repository repository, ObjectId objectId) {
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = getParseCommit(walk, objectId);
            if (commit == null) {
                return null;
            }
            RevTree tree = commit.getTree();
            return iterableFromRepoAndRevTree(repository, tree);
        }
    }

    private Iterable<String> iterableFromRepoAndRevTree(Repository repository, RevTree tree) {
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            return iterableFromTreeWalk(treeWalk);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Iterable<String> iterableFromTreeWalk(TreeWalk treeWalk) {
        return () -> new Iterator<String>() {
            private Boolean hasNext;

            @Override
            public boolean hasNext() {
                if (hasNext == null) {
                    try {
                        hasNext = treeWalk.next();
                    } catch (IOException e) {
                        hasNext = false;
                    }
                }
                return hasNext;
            }

            @Override
            public String next() {
                hasNext = null;
                return treeWalk.getPathString();
            }
        };
    }
}
