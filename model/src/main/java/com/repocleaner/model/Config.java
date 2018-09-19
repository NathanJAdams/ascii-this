package com.repocleaner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Config {
    private Map<String, String> languageVersions;
    private Map<String, Boolean> splitTypes;

    public Iterable<SplitType> getSplitTypes() {
        return splitTypes.keySet()
                .stream()
                .map(SplitType::valueOf)
                .collect(Collectors.toSet());
    }
}
