package com.repocleaner.apigateway.models;

import com.amazonaws.services.apigatewayv2.model.ContentHandlingStrategy;
import com.amazonaws.services.apigatewayv2.model.IntegrationType;
import com.amazonaws.services.apigatewayv2.model.PassthroughBehavior;
import com.repocleaner.apigateway.ContentType;
import com.repocleaner.apigateway.HttpMethod;
import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.beans.IntegrationAwsModel;
import com.repocleaner.apigateway.beans.IntegrationResponseAwsModel;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntegrationModel implements Model<IntegrationAwsModel> {
    private final String role;
    private final String functionName;
    private final Map<String, IntegrationResponseModel> responses;
    private final PassthroughBehavior passthroughBehavior;
    private final HttpMethod httpMethod;
    private final ContentHandlingStrategy contentHandlingStrategy;
    private final Map<ContentType, String> requestTemplates;
    private final IntegrationType integrationType;

    @Override
    public IntegrationAwsModel convert() {
        return new IntegrationAwsModel(convertToCredentials(), convertToUri(), convertResponses(), enumName(passthroughBehavior, true), enumName(httpMethod, false), enumName(contentHandlingStrategy, false), convertRequestTemplates(), enumName(integrationType, true));
    }

    private String convertToCredentials() {
        return (role == null)
                ? null
                : "arn:aws:iam::474774868333:role/" + role;
    }

    private String convertToUri() {
        return (functionName == null)
                ? null
                : "arn:aws:apigateway:eu-west-1:lambda:path/2015-03-31/functions/arn:aws:lambda:eu-west-1:474774868333:function:" + functionName + "/invocations";
    }

    private Map<String, IntegrationResponseAwsModel> convertResponses() {
        if (responses == null) {
            return null;
        }
        Map<String, IntegrationResponseAwsModel> responses = new HashMap<>();
        for (Map.Entry<String, IntegrationResponseModel> entry : this.responses.entrySet()) {
            responses.put(entry.getKey(), entry.getValue().convert());
        }
        return responses;
    }

    private Map<String, String> convertRequestTemplates() {
        if (requestTemplates == null) {
            return null;
        }
        Map<String, String> responses = new HashMap<>();
        for (Map.Entry<ContentType, String> entry : this.requestTemplates.entrySet()) {
            responses.put(entry.getKey().getDescription(), entry.getValue());
        }
        return responses;
    }

    private String enumName(Enum<?> enumValue, boolean toLower) {
        if (enumValue == null) {
            return null;
        }
        String name = enumValue.name();
        return (toLower)
                ? name.toLowerCase()
                : name;
    }
}
