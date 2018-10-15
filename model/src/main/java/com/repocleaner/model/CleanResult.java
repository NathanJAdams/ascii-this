package com.repocleaner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CleanResult {
    private final int creditCost;
    private final String description;
}
