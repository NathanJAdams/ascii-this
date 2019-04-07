package com.repocleaner.apigateway.models;

import com.repocleaner.apigateway.InputLocation;
import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.beans.ParameterAwsModel;
import com.repocleaner.apigateway.beans.TypeRefClassAwsModel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParameterModel implements Model<ParameterAwsModel> {
    private final String name;
    private final InputLocation in;
    private final boolean required;
    private final Class<?> parameterType;

    @Override
    public ParameterAwsModel convert() {
        String inString = in.name().toLowerCase();
        String type = JsonTypeNames.getTypeName(parameterType);
        TypeRefClassAwsModel schema = JsonTypeNames.isPrimitiveJsonType(parameterType)
                ? null
                : TypeRefConverter.convertFieldClass(parameterType);
        return new ParameterAwsModel(name, inString, required, type, schema);
    }
}
