package com.repocleaner.util;

public class RepoCleanerException extends Exception {
    public RepoCleanerException(String message) {
        super(message);
    }

    public RepoCleanerException(String message, Throwable cause) {
        super(message, cause);
    }
}
