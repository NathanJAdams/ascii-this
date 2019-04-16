package com.repocleaner.lambdaretrievediff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Diff {
    private final FileDiff[] fileDiffs;
}
