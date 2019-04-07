package com.repocleaner.apigateway.beans;

import com.repocleaner.apigateway.models.TypeRefConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeRefArrayAwsModel implements TypeRefAwsModel {
    private final String type = "array";
    private final TypeRefAwsModel items;

    public TypeRefArrayAwsModel(Class<?> componentClass) {
        this.items = TypeRefConverter.convertClass(componentClass);
    }
}
