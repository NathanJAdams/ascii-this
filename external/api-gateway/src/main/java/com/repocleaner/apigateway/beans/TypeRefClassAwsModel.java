package com.repocleaner.apigateway.beans;

import com.google.gson.annotations.SerializedName;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeRefClassAwsModel implements TypeRefAwsModel {
    @SerializedName("$ref")
    private final String ref;

    public TypeRefClassAwsModel(Class<?> refClass) {
        this.ref = "#/definitions/" + refClass.getSimpleName();
    }
}
