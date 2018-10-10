package com.repocleaner.model.transform;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class TransformationInfo {
    private final RiskType riskType;
    private final EffectType effectType;
}
