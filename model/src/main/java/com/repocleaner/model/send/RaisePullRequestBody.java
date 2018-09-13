package com.repocleaner.model.send;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RaisePullRequestBody {
    private String title;
    private String body;
    private String head;
    private String base;
}
