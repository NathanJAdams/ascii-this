package com.repocleaner.clean.website.response;

public class TransformationResponse {
    private final boolean success;
    private final boolean isStarted;
    private final boolean isCompleted;
    private final String gitDiff;

    public TransformationResponse(boolean success, boolean isStarted, boolean isCompleted, String gitDiff) {
        this.success = success;
        this.isStarted = isStarted;
        this.isCompleted = isCompleted;
        this.gitDiff = gitDiff;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getGitDiff() {
        return gitDiff;
    }
}
