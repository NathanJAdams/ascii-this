package com.repocleaner.apigateway.models;

import com.repocleaner.apigateway.ContentType;
import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.RequestValidatorType;
import com.repocleaner.apigateway.beans.IntegrationAwsModel;
import com.repocleaner.apigateway.beans.ParameterAwsModel;
import com.repocleaner.apigateway.beans.ResourceAwsModel;
import com.repocleaner.apigateway.beans.ResponseAwsModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceModel implements Model<ResourceAwsModel> {
    private final String operationId;
    private final List<ContentType> consumes;
    private final List<ContentType> produces;
    private final Map<String, ResponseModel> responses;
    private final List<ParameterModel> parameters;
    private final RequestValidatorType requestValidator;
    private final IntegrationModel integration;

    @Override
    public ResourceAwsModel convert() {
        List<String> consumes = convertContentTypes(this.consumes);
        List<String> produces = convertContentTypes(this.produces);
        String validator = (requestValidator==null)
                ?null
                :requestValidator.name();
        IntegrationAwsModel integrationAwsModel = (integration == null)
                ? null
                : integration.convert();
        return new ResourceAwsModel(operationId, consumes, produces, createParameters(), createResponses(), validator, integrationAwsModel);
    }

    private List<String> convertContentTypes(List<ContentType> contentTypes) {
        List<String> producesList = new ArrayList<>();
        for (ContentType contentType : contentTypes) {
            producesList.add(contentType.getDescription());
        }
        return producesList;
    }

    private Map<String, ResponseAwsModel> createResponses() {
        if (this.responses == null) {
            return null;
        }
        Map<String, ResponseAwsModel> responses = new HashMap<>();
        for (Map.Entry<String, ResponseModel> entry : this.responses.entrySet()) {
            ResponseAwsModel responseAwsModel = entry.getValue().convert();
            responses.put(entry.getKey(), responseAwsModel);
        }
        return responses;
    }

    private List<ParameterAwsModel> createParameters() {
        if (this.parameters == null) {
            return null;
        }
        List<ParameterAwsModel> parameters = new ArrayList<>();
        for (ParameterModel parameter : this.parameters) {
            parameters.add(parameter.convert());
        }
        return parameters;
    }
}
