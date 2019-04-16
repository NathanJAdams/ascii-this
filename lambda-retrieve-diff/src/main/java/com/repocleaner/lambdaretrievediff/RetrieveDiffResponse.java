package com.repocleaner.lambdaretrievediff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RetrieveDiffResponse {
    private final Diff diff;
}
