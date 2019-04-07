package com.repocleaner.lambdaretrievediff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class FilePartDiff {
    private final String[] a;
    private final String[] b;
    private final String[] common;
}
