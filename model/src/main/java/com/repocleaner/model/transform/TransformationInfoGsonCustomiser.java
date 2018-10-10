package com.repocleaner.model.transform;

import com.google.gson.GsonBuilder;
import com.repocleaner.model.transform.TransformationInfo;
import com.repocleaner.model.transform.transformations.EOFTransformationInfo;
import com.repocleaner.model.transform.transformations.LicenseTransformationInfo;
import com.repocleaner.util.json.GsonCustomiser;
import com.repocleaner.util.json.RuntimeTypeAdapterFactory;

public class TransformationInfoGsonCustomiser implements GsonCustomiser {
    @Override
    public void customise(GsonBuilder builder) {
        RuntimeTypeAdapterFactory<TransformationInfo> factory = new RuntimeTypeAdapterFactory<>(TransformationInfo.class);
        factory.registerSubtype(EOFTransformationInfo.class, "eof");
        factory.registerSubtype(LicenseTransformationInfo.class, "license");
        builder.registerTypeAdapterFactory(factory);
    }
}
