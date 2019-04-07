package com.repocleaner.apigateway.beans;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestApiAwsModel {
    private final String swagger = "2.0";
    private final InfoAwsModel info;
    private final String basePath;
    private final List<String> schemes = Arrays.asList("https");
    private final Map<String, Map<String, ResourceAwsModel>> paths;
    private final Map<String, TypeAwsModel> definitions;
    @SerializedName("x-amazon-apigateway-request-validators")
    private final Map<String, RequestValidatorAwsModel> validators;
}
