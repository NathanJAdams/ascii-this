package com.repocleaner.model.send;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RaisePullRequestBody {
    private final String title;
    private final String body;
    private final String head;
    private final String base;
}
