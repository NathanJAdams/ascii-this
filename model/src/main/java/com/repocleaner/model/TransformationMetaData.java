package com.repocleaner.model;

import com.repocleaner.model.transform.EffectType;
import com.repocleaner.model.transform.RiskType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class TransformationMetaData {
    private final RiskType riskType;
    private final EffectType effectType;
    private final int priority;
}
