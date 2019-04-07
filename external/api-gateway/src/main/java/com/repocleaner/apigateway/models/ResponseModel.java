package com.repocleaner.apigateway.models;

import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.beans.HeaderTypeAwsModel;
import com.repocleaner.apigateway.beans.ParameterAwsModel;
import com.repocleaner.apigateway.beans.ResponseAwsModel;
import com.repocleaner.apigateway.beans.TypeRefClassAwsModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseModel implements Model<ResponseAwsModel> {
    private final String description;
    private final Class<?> schemaClass;
    private final List<String> headers;
    private final List<ParameterModel> parameters;

    @Override
    public ResponseAwsModel convert() {
        TypeRefClassAwsModel schema = new TypeRefClassAwsModel(schemaClass);
        return new ResponseAwsModel(description, schema, createHeaders(), createParameters());
    }

    private Map<String, HeaderTypeAwsModel> createHeaders() {
        if (this.headers == null) {
            return null;
        }
        Map<String, HeaderTypeAwsModel> headers = new HashMap<>();
        for (String header : this.headers) {
            HeaderTypeAwsModel headerTypeAwsModel = new HeaderTypeAwsModel();
            headers.put(header, headerTypeAwsModel);
        }
        return headers;
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
