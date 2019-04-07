package com.repocleaner.apigateway.beans;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParameterAwsModel {
    private final String name;
    private final String in;
    private final boolean required;
    private final String type;
    private final TypeRefClassAwsModel schema;
}
