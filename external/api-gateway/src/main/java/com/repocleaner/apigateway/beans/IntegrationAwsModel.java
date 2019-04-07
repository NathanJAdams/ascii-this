package com.repocleaner.apigateway.beans;

import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntegrationAwsModel {
    private final String credentials;
    private final String uri;
    private final Map<String, IntegrationResponseAwsModel> responses;
    private final String passthroughBehavior;
    private final String httpMethod;
    private final String contentHandling;
    private final Map<String, String> requestTemplates;
    private final String type;
}
