package com.repocleaner.json.customisers;

import com.google.gson.GsonBuilder;
import com.repocleaner.model.TransformationMetaData;
import com.repocleaner.transformation.transformations.EOFTransformationMetaData;
import com.repocleaner.transformation.transformations.LicenseTransformationMetaData;
import com.repocleaner.util.json.GsonCustomiser;
import com.repocleaner.util.json.RuntimeTypeAdapterFactory;

public class TransformationInfoGsonCustomiser implements GsonCustomiser {
    @Override
    public void customise(GsonBuilder builder) {
        RuntimeTypeAdapterFactory<TransformationMetaData> factory = new RuntimeTypeAdapterFactory<>(TransformationMetaData.class);
        factory.registerSubtype(EOFTransformationMetaData.class, "eof");
        factory.registerSubtype(LicenseTransformationMetaData.class, "license");
        builder.registerTypeAdapterFactory(factory);
    }
}
