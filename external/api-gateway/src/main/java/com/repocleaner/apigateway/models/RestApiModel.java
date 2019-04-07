package com.repocleaner.apigateway.models;

import com.repocleaner.apigateway.HttpMethod;
import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.RequestValidatorType;
import com.repocleaner.apigateway.beans.InfoAwsModel;
import com.repocleaner.apigateway.beans.RequestValidatorAwsModel;
import com.repocleaner.apigateway.beans.ResourceAwsModel;
import com.repocleaner.apigateway.beans.RestApiAwsModel;
import com.repocleaner.apigateway.beans.TypeAwsModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestApiModel implements Model<RestApiAwsModel> {
    private final InfoModel info;
    private final String basePath;
    private final Map<String, Map<HttpMethod, ResourceModel>> paths;
    private final List<DefinitionModel> definitions;
    private final Map<RequestValidatorType, RequestValidatorModel> requestValidators;

    @Override
    public RestApiAwsModel convert() {
        InfoAwsModel info = this.info.convert();
        Map<String, Map<String, ResourceAwsModel>> paths = convertPaths();
        Map<String, TypeAwsModel> definitions = convertDefinitions();
        Map<String, RequestValidatorAwsModel> validators = convertValidators();
        return new RestApiAwsModel(info, basePath, paths, definitions, validators);
    }

    private Map<String, Map<String, ResourceAwsModel>> convertPaths() {
        Map<String, Map<String, ResourceAwsModel>> paths = new HashMap<>();
        for (Map.Entry<String, Map<HttpMethod, ResourceModel>> pathEntry : this.paths.entrySet()) {
            Map<String, ResourceAwsModel> resourceAwsModels = new HashMap<>();
            for (Map.Entry<HttpMethod, ResourceModel> resourceEntry : pathEntry.getValue().entrySet()) {
                ResourceAwsModel resourceAwsModel = resourceEntry.getValue().convert();
                resourceAwsModels.put(resourceEntry.getKey().name().toLowerCase(), resourceAwsModel);
            }
            paths.put(pathEntry.getKey(), resourceAwsModels);
        }
        return paths;
    }

    private Map<String, TypeAwsModel> convertDefinitions() {
        Map<String, TypeAwsModel> definitions = new HashMap<>();
        for (DefinitionModel definition : this.definitions) {
            TypeAwsModel definitionAwsModel = definition.convert();
            definitions.put(definition.getSchemaClass().getSimpleName(), definitionAwsModel);
        }
        return definitions;
    }

    private Map<String, RequestValidatorAwsModel> convertValidators() {
        if (this.requestValidators == null) {
            return null;
        }
        Map<String, RequestValidatorAwsModel> validators = new HashMap<>();
        for (Map.Entry<RequestValidatorType, RequestValidatorModel> entry : this.requestValidators.entrySet()) {
            validators.put(entry.getKey().name(), entry.getValue().convert());
        }
        return validators;
    }
}
