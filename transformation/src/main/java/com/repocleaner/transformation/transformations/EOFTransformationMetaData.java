package com.repocleaner.transformation.transformations;

import com.repocleaner.model.TransformationMetaData;
import com.repocleaner.model.transform.EffectType;
import com.repocleaner.model.transform.RiskType;
import lombok.Getter;

@Getter
public class EOFTransformationMetaData extends TransformationMetaData {
    private final String eof;

    public EOFTransformationMetaData(int priority, String eof) {
        super(RiskType.None, EffectType.Comment, priority);
        this.eof = eof;
    }
}
