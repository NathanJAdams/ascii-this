package com.repocleaner.clean.host;

public class RaisePullRequestBody {
    private final String title;
    private final String body;
    private final String head;
    private final String base;

    public RaisePullRequestBody(String title, String body, String head, String base) {
        this.title = title;
        this.body = body;
        this.head = head;
        this.base = base;
    }
}
