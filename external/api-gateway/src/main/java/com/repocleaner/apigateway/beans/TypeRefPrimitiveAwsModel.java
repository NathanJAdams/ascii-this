package com.repocleaner.apigateway.beans;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeRefPrimitiveAwsModel implements TypeRefAwsModel {
    private final String type;
}
