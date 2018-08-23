package com.repocleaner.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CleanResult {
    private final int tokenCost;
    private final String description;
}
