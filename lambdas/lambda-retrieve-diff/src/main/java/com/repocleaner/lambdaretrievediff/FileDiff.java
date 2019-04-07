package com.repocleaner.lambdaretrievediff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class FileDiff {
    private final String fileNameA;
    private final String fileNameB;
    private final FilePartDiff[] filePartDiffs;
}
