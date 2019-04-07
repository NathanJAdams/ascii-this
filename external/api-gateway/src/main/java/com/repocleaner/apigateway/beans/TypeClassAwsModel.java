package com.repocleaner.apigateway.beans;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeClassAwsModel implements TypeAwsModel {
    private final String type = "object";
    private final Map<String, TypeRefAwsModel> properties;
    private final List<String> required;
}
