package com.repocleaner.apigateway.beans;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypePrimitiveAwsModel implements TypeAwsModel {
    private final String type;
}
