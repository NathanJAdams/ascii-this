package com.repocleaner.apigateway.deployments;

import com.amazonaws.services.apigatewayv2.model.ContentHandlingStrategy;
import com.amazonaws.services.apigatewayv2.model.IntegrationType;
import com.amazonaws.services.apigatewayv2.model.PassthroughBehavior;
import com.repocleaner.apigateway.ContentType;
import com.repocleaner.apigateway.Deployment;
import com.repocleaner.apigateway.HttpMethod;
import com.repocleaner.apigateway.InputLocation;
import com.repocleaner.apigateway.ModelCreator;
import com.repocleaner.apigateway.RequestValidatorType;
import com.repocleaner.apigateway.models.DefinitionModel;
import com.repocleaner.apigateway.models.InfoModel;
import com.repocleaner.apigateway.models.IntegrationModel;
import com.repocleaner.apigateway.models.IntegrationResponseModel;
import com.repocleaner.apigateway.models.ParameterModel;
import com.repocleaner.apigateway.models.ResourceModel;
import com.repocleaner.apigateway.models.ResponseModel;
import com.repocleaner.apigateway.models.RestApiModel;
import com.repocleaner.apigateway.schemas.EmptySchema;
import com.repocleaner.apigateway.schemas.EncryptedTokenSchema;
import com.repocleaner.apigateway.schemas.ErrorSchema;
import com.repocleaner.apigateway.schemas.TokenSchema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncryptTokenDeployment implements Deployment {
    @Override
    public RestApiModel createRestApiModel() {
        String basePath = "/prod";
        return new RestApiModel(createInfo(), basePath, createPaths(), createDefinitions(), ModelCreator.REQUEST_VALIDATORS);
    }

    private InfoModel createInfo() {
        return new InfoModel(LocalDateTime.now(), "encrypt-token");
    }

    private Map<String, Map<HttpMethod, ResourceModel>> createPaths() {
        Map<String, Map<HttpMethod, ResourceModel>> paths = new HashMap<>();
        paths.put("/encrypt-token", createRootResources());
        return paths;
    }

    private Map<HttpMethod, ResourceModel> createRootResources() {
        Map<HttpMethod, ResourceModel> resources = new HashMap<>();
        resources.put(HttpMethod.OPTIONS, ModelCreator.CORS_OPTIONS_RESOURCE);
        resources.put(HttpMethod.POST, createRootPostResource());
        return resources;
    }

    private ResourceModel createRootPostResource() {
        String operationId = "EncryptToken";
        List<ContentType> consumes = Arrays.asList(ContentType.JSON);
        List<ContentType> produces = Arrays.asList(ContentType.JSON);
        return new ResourceModel(
                operationId,
                consumes,
                produces,
                createRootPostResponses(),
                createRootPostParameters(),
                RequestValidatorType.BODY,
                createIntegration());
    }

    private Map<String, ResponseModel> createRootPostResponses() {
        Map<String, ResponseModel> responses = new HashMap<>();
        responses.put("200", createEncryptedTokenResponse());
        responses.put("400", ModelCreator.ERROR_RESPONSE);
        responses.put("500", ModelCreator.ERROR_RESPONSE);
        return responses;
    }

    private List<ParameterModel> createRootPostParameters() {
        List<ParameterModel> parameters = new ArrayList<>();
        parameters.add(new ParameterModel("Token", InputLocation.BODY, true, TokenSchema.class));
        return parameters;
    }

    private ResponseModel createEncryptedTokenResponse() {
        return new ResponseModel("Encrypted Token", EncryptedTokenSchema.class, ModelCreator.CORS_RESPONSE_HEADERS, null);
    }

    private IntegrationModel createIntegration() {
        String role = "RepoCleanerRole";
        Map<String, IntegrationResponseModel> responses = new HashMap<>();
        responses.put("default", new IntegrationResponseModel(
                200,
                ContentHandlingStrategy.CONVERT_TO_TEXT,
                null,
                ModelCreator.CORS_INTEGRATION_RESPONSE_PARAMETERS));
        return new IntegrationModel(
                role,
                "encrypt-token",
                responses,
                PassthroughBehavior.WHEN_NO_MATCH,
                HttpMethod.POST,
                ContentHandlingStrategy.CONVERT_TO_TEXT,
                null,
                IntegrationType.AWS);
    }

    private List<DefinitionModel> createDefinitions() {
        return ModelCreator.createDefinitions(EmptySchema.class, ErrorSchema.class, TokenSchema.class, EncryptedTokenSchema.class);
    }
}
