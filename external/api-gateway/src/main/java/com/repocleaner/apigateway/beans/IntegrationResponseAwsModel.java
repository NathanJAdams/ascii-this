package com.repocleaner.apigateway.beans;

import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntegrationResponseAwsModel {
    private final String statusCode;
    private final String contentHandling;
    private final Map<String, String> responseTemplates;
    private final Map<String, String> responseParameters;
}
