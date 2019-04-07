package com.repocleaner.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public class GitUtil {
    public static final PersonIdent REPO_CLEANER_AUTHOR = new PersonIdent("repocleaner.com", "repo.cleaner@gmail.com");

    public static Git init(File folder) throws RepoCleanerException {
        try {
            return Git.init()
                    .setDirectory(folder)
                    .call();
        } catch (GitAPIException e) {
            throw new RepoCleanerException("Failed to init folder", e);
        }
    }

    public static Git open(File folder) throws RepoCleanerException {
        try {
            return Git.open(folder);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to open folder", e);
        }
    }

    public static Git clone(String uri, String branch, File downloadFolder) throws RepoCleanerException {
        try {
            return Git.cloneRepository()
                    .setCredentialsProvider(CredentialsProvider.getDefault())
                    .setURI(uri)
                    .setBranchesToClone(Collections.singleton(branch))
                    .setBranch(branch)
                    .setCloneSubmodules(true)
                    .setDirectory(downloadFolder)
                    .call();
        } catch (GitAPIException e) {
            throw new RepoCleanerException("Failed to clone", e);
        }
    }

    public static String getBranchName(Git git) throws RepoCleanerException {
        try {
            return git.getRepository()
                    .getBranch();
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to find branch name", e);
        }
    }

    public static Set<String> getBranchNames(Git git) throws RepoCleanerException {
        try {
            return git.branchList()
                    .setListMode(ListBranchCommand.ListMode.ALL)
                    .call()
                    .stream()
                    .map(Ref::getName)
                    .map(fullName -> fullName.substring(fullName.lastIndexOf('/') + 1))
                    .collect(Collectors.toSet());
        } catch (GitAPIException e) {
            throw new RepoCleanerException("Failed to find branch names", e);
        }
    }

    public static void checkoutNewBranch(Git git, String branchName) throws RepoCleanerException {
        try {
            git.checkout()
                    .setCreateBranch(true)
                    .setName(branchName)
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            throw new RepoCleanerException("Failed to create branch:" + branchName);
        }
    }

    public static void addAll(Git git) throws RepoCleanerException {
        try {
            git.add()
                    .addFilepattern("*")
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            throw new RepoCleanerException("Failed to add files", e);
        }
    }

    public static void commit(Git git, String message) throws RepoCleanerException {
        try {
            git.commit()
                    .setAll(true)
                    .setAuthor(REPO_CLEANER_AUTHOR)
                    .setCommitter(REPO_CLEANER_AUTHOR)
                    .setMessage(message)
                    .call();
        } catch (GitAPIException e) {
            throw new RepoCleanerException("Failed to commit", e);
        }
    }

    public static void push(Git git, String token) throws RepoCleanerException {
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(GitUtil.REPO_CLEANER_AUTHOR.getName(), token);
        try {
            git.push()
                    .setCredentialsProvider(credentialsProvider)
                    .call();
        } catch (GitAPIException e) {
            throw new RepoCleanerException("Failed to push", e);
        }
    }

    public static String getLatestDiff(Git git) throws RepoCleanerException {
        RevCommit[] last2Commits = getLast2Commits(git);
        AbstractTreeIterator latestTreeIterator = getTreeIterator(git, last2Commits[0]);
        AbstractTreeIterator previousTreeIterator = getTreeIterator(git, last2Commits[1]);
        OutputStream outputStream = new ByteArrayOutputStream();
        try (DiffFormatter formatter = new DiffFormatter(outputStream)) {
            formatter.setRepository(git.getRepository());
            formatter.format(previousTreeIterator, latestTreeIterator);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to format diff", e);
        }
        return outputStream.toString();
    }

    private static AbstractTreeIterator getTreeIterator(Git git, RevCommit revCommit) throws RepoCleanerException {
        ObjectId treeId = revCommit.getTree().getId();
        try (ObjectReader reader = git.getRepository().newObjectReader()) {
            return new CanonicalTreeParser(null, reader, treeId);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to get tree iterator", e);
        }
    }

    private static RevCommit[] getLast2Commits(Git git) throws RepoCleanerException {
        RevCommit[] last2Commits = new RevCommit[2];
        try {
            Iterator<RevCommit> revCommits = git.log().all().call().iterator();
            if (revCommits.hasNext()) {
                last2Commits[0] = revCommits.next();
            } else {
                throw new RepoCleanerException("Zero commits, could not create a diff");
            }
            if (revCommits.hasNext()) {
                last2Commits[1] = revCommits.next();
            } else {
                throw new RepoCleanerException("One commit, could not create a diff");
            }
        } catch (IOException | GitAPIException e) {
            throw new RepoCleanerException("Failed to get diff", e);
        }
        return last2Commits;
    }
}
