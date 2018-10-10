package com.repocleaner.model.transform.transformations;

import com.repocleaner.model.transform.EffectType;
import com.repocleaner.model.transform.RiskType;
import com.repocleaner.model.transform.TransformationInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EOFTransformationInfo extends TransformationInfo {
    private String eof;

    public EOFTransformationInfo() {
        super(RiskType.None, EffectType.Comment);
    }
}
