package com.repocleaner.apigateway;

import com.amazonaws.services.apigatewayv2.model.ContentHandlingStrategy;
import com.amazonaws.services.apigatewayv2.model.IntegrationType;
import com.amazonaws.services.apigatewayv2.model.PassthroughBehavior;
import com.repocleaner.apigateway.models.DefinitionModel;
import com.repocleaner.apigateway.models.IntegrationModel;
import com.repocleaner.apigateway.models.IntegrationResponseModel;
import com.repocleaner.apigateway.models.RequestValidatorModel;
import com.repocleaner.apigateway.models.ResourceModel;
import com.repocleaner.apigateway.models.ResponseModel;
import com.repocleaner.apigateway.schemas.EmptySchema;
import com.repocleaner.apigateway.schemas.ErrorSchema;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelCreator {
    public static final List<String> CORS_RESPONSE_HEADERS;
    public static final Map<String, String> CORS_INTEGRATION_RESPONSE_PARAMETERS;
    public static final ResourceModel CORS_OPTIONS_RESOURCE;
    public static final ResponseModel ERROR_RESPONSE;
    public static final Map<RequestValidatorType, RequestValidatorModel> REQUEST_VALIDATORS;

    static {
        CORS_RESPONSE_HEADERS = Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Access-Control-Allow-Methods");

        CORS_INTEGRATION_RESPONSE_PARAMETERS = new HashMap<>();
        CORS_INTEGRATION_RESPONSE_PARAMETERS.put("Access-Control-Allow-Headers", "'Content-Type'");
        CORS_INTEGRATION_RESPONSE_PARAMETERS.put("Access-Control-Allow-Methods", "'POST,GET,OPTIONS'");
        CORS_INTEGRATION_RESPONSE_PARAMETERS.put("Access-Control-Allow-Origin", "'http://www.repocleaner.com'");

        List<ContentType> consumes = Arrays.asList(ContentType.JSON);
        List<ContentType> produces = consumes;
        Map<ContentType, String> integrationResponseTemplates = new HashMap<>();
        integrationResponseTemplates.put(ContentType.JSON, "{}");
        Map<String, IntegrationResponseModel> integrationResponses = new HashMap<>();
        IntegrationResponseModel integrationResponse = new IntegrationResponseModel(200, null, integrationResponseTemplates, CORS_INTEGRATION_RESPONSE_PARAMETERS);
        integrationResponses.put("default", integrationResponse);
        Map<ContentType, String> requestTemplates = new HashMap<>();
        requestTemplates.put(ContentType.JSON, "{ \"statusCode\": 200 }");
        IntegrationModel integration = new IntegrationModel(null, null, integrationResponses, PassthroughBehavior.WHEN_NO_MATCH, HttpMethod.OPTIONS, ContentHandlingStrategy.CONVERT_TO_TEXT, requestTemplates, IntegrationType.MOCK);
        Map<String, ResponseModel> responses = new HashMap<>();
        ResponseModel response = new ResponseModel("description", EmptySchema.class, CORS_RESPONSE_HEADERS, null);
        responses.put("200", response);
        CORS_OPTIONS_RESOURCE = new ResourceModel(null, consumes, produces, responses, null, null, integration);

        ERROR_RESPONSE = new ResponseModel("Error", ErrorSchema.class, ModelCreator.CORS_RESPONSE_HEADERS, null);

        Map<RequestValidatorType, RequestValidatorModel> requestValidators = new HashMap<>();
        requestValidators.put(RequestValidatorType.NONE, new RequestValidatorModel(false, false));
        requestValidators.put(RequestValidatorType.BODY, new RequestValidatorModel(true, false));
        requestValidators.put(RequestValidatorType.PARAMS, new RequestValidatorModel(false, true));
        requestValidators.put(RequestValidatorType.BOTH, new RequestValidatorModel(true, true));
        REQUEST_VALIDATORS = Collections.unmodifiableMap(requestValidators);
    }

    @SafeVarargs
    public static List<DefinitionModel> createDefinitions(Class<? extends Schema>... schemaClasses) {
        List<DefinitionModel> definitions = new ArrayList<>();
        for (Class<? extends Schema> schemaClass : schemaClasses) {
            definitions.add(new DefinitionModel(schemaClass));
        }
        return definitions;
    }
}
