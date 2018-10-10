package com.repocleaner.model.transform.transformers;

import com.repocleaner.model.transform.EffectType;
import com.repocleaner.model.transform.RiskType;
import com.repocleaner.model.transform.TransformerInfo;

public class EOFTransformerInfo extends TransformerInfo {
    public EOFTransformerInfo() {
        super(RiskType.None, EffectType.Comment);
    }
}
