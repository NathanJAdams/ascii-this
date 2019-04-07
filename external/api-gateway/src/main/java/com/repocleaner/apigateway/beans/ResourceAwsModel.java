package com.repocleaner.apigateway.beans;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceAwsModel {
    private final String operationId;
    private final List<String> consumes;
    private final List<String> produces;
    private final List<ParameterAwsModel> parameters;
    private final Map<String, ResponseAwsModel> responses;
    @SerializedName("x-amazon-apigateway-request-validator")
    private final String requestValidator;
    @SerializedName("x-amazon-apigateway-integration")
    private final IntegrationAwsModel integration;
}
