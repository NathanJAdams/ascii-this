package com.repocleaner.apigateway.models;

import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.beans.RequestValidatorAwsModel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestValidatorModel implements Model<RequestValidatorAwsModel> {
    private final boolean validateRequestBody;
    private final boolean validateRequestParameters;

    @Override
    public RequestValidatorAwsModel convert() {
        return new RequestValidatorAwsModel(validateRequestBody, validateRequestParameters);
    }
}
