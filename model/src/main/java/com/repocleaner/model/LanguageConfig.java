package com.repocleaner.model;

import com.repocleaner.model.transform.SplitType;
import com.repocleaner.model.transform.TransformationInfo;
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
public class LanguageConfig {
    private String version;
    private Map<String, Boolean> splitTypes;
    private Map<String, TransformationInfo> transformationInfos;

    public Iterable<SplitType> getSplitTypes() {
        return splitTypes.keySet()
                .stream()
                .map(SplitType::valueOf)
                .collect(Collectors.toSet());
    }
}
