package com.repocleaner.model.transform.transformations;

import com.repocleaner.model.transform.EffectType;
import com.repocleaner.model.transform.RiskType;
import com.repocleaner.model.transform.TransformationInfo;
import lombok.Getter;

@Getter
public class EOFTransformationInfo extends TransformationInfo {
    private final String eof;

    public EOFTransformationInfo(int priority, String eof) {
        super(RiskType.None, EffectType.Comment, priority);
        this.eof = eof;
    }
}
