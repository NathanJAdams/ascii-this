package com.repocleaner.model.transform.transformations;

import com.repocleaner.model.transform.EffectType;
import com.repocleaner.model.transform.LicenseType;
import com.repocleaner.model.transform.RiskType;
import com.repocleaner.model.transform.TransformationInfo;
import com.repocleaner.util.IOUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class LicenseTransformationInfo extends TransformationInfo {
    private static final Map<LicenseType, String> LICENSE_TYPE_STRING_MAP = new HashMap<>();

    private String license;

    public LicenseTransformationInfo() {
        super(RiskType.None, EffectType.Comment);
    }

    public void setLicenseType(LicenseType licenseType) {
        String license = LICENSE_TYPE_STRING_MAP.get(licenseType);
        if (license == null) {
            String fileName = licenseType.name() + "License.txt";
            try (InputStream is = LicenseTransformationInfo.class.getResourceAsStream(fileName)) {
                license = IOUtils.toString(is, StandardCharsets.UTF_8);
                LICENSE_TYPE_STRING_MAP.put(licenseType, license);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.license = license;
    }
}
