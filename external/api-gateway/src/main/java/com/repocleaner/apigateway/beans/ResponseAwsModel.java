package com.repocleaner.apigateway.beans;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseAwsModel {
    private final String description;
    private final TypeRefClassAwsModel schema;
    private final Map<String, HeaderTypeAwsModel> headers;
    private final List<ParameterAwsModel> parameters;
}
