package com.repocleaner.lambdaretrievediff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PartDiff {
    private final String a;
    private final String b;
}
