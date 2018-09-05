package com.repocleaner.model.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TransformationPriority {
    private Map<String, List<TransformationType>> priorities;
}