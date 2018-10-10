package com.repocleaner.model.transform.transformers;

import com.repocleaner.model.transform.EffectType;
import com.repocleaner.model.transform.LicenseType;
import com.repocleaner.model.transform.RiskType;
import com.repocleaner.model.transform.TransformerInfo;
import lombok.Getter;

@Getter
public class LicenseTransformerInfo extends TransformerInfo {
    private final LicenseType licenseType;

    public LicenseTransformerInfo(LicenseType licenseType) {
        super(RiskType.None, EffectType.Comment);
        this.licenseType = licenseType;
    }
}
