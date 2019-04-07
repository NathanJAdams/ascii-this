package com.repocleaner.apigateway.beans;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestValidatorAwsModel {
    private final boolean validateRequestBody;
    private final boolean validateRequestParameters;
}
