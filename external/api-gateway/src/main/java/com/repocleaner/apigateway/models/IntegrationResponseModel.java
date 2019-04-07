package com.repocleaner.apigateway.models;

import com.amazonaws.services.apigatewayv2.model.ContentHandlingStrategy;
import com.repocleaner.apigateway.ContentType;
import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.beans.IntegrationResponseAwsModel;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntegrationResponseModel implements Model<IntegrationResponseAwsModel> {
    private final int statusCode;
    private final ContentHandlingStrategy contentHandlingStrategy;
    private final Map<ContentType, String> responseTemplates;
    private final Map<String, String> responseParameters;

    @Override
    public IntegrationResponseAwsModel convert() {
        return new IntegrationResponseAwsModel(String.valueOf(statusCode), enumName(contentHandlingStrategy), convertResponseTemplates(), convertResponseParameters());
    }

    private String enumName(Enum<?> enumValue) {
        if (enumValue == null) {
            return null;
        }
        return enumValue.name();
    }

    private Map<String, String> convertResponseTemplates() {
        if (this.responseTemplates == null) {
            return null;
        }
        Map<String, String> responseTemplates = new HashMap<>();
        for (Map.Entry<ContentType, String> entry : this.responseTemplates.entrySet()) {
            responseTemplates.put(entry.getKey().getDescription(), entry.getValue());
        }
        return responseTemplates;
    }

    private Map<String, String> convertResponseParameters() {
        if (this.responseParameters == null) {
            return null;
        }
        Map<String, String> responseParameters = new HashMap<>();
        for (Map.Entry<String, String> entry : this.responseParameters.entrySet()) {
            responseParameters.put("method.response.header." + entry.getKey(), entry.getValue());
        }
        return responseParameters;
    }
}
