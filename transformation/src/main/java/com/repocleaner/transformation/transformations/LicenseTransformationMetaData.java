package com.repocleaner.transformation.transformations;

import com.repocleaner.model.TransformationMetaData;
import com.repocleaner.model.transform.EffectType;
import com.repocleaner.model.transform.LicenseType;
import com.repocleaner.model.transform.RiskType;
import com.repocleaner.util.IOUtils;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Getter
public class LicenseTransformationMetaData extends TransformationMetaData {
    private static final Map<LicenseType, String> LICENSE_TYPE_STRING_MAP = new HashMap<>();

    private final String license;

    public LicenseTransformationMetaData(int priority, LicenseType licenseType) {
        super(RiskType.None, EffectType.Comment, priority);
        this.license = getLicense(licenseType);
    }

    private static String getLicense(LicenseType licenseType) {
        String license = LICENSE_TYPE_STRING_MAP.get(licenseType);
        if (license == null) {
            String fileName = licenseType.name() + "License.txt";
            try (InputStream is = LicenseTransformationMetaData.class.getResourceAsStream(fileName)) {
                license = IOUtils.toString(is, StandardCharsets.UTF_8);
                LICENSE_TYPE_STRING_MAP.put(licenseType, license);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return license;
    }
}
