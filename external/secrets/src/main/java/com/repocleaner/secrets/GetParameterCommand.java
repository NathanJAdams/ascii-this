package com.repocleaner.secrets;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetParameterCommand {
    private final String parameterName;

    public String getParameter() {
        AWSSimpleSystemsManagement client = AWSSimpleSystemsManagementClientBuilder.defaultClient();
        GetParameterRequest request = new GetParameterRequest();
        request.setName(parameterName);
        request.setWithDecryption(true);
        GetParameterResult result = client.getParameter(request);
        Parameter parameter = result.getParameter();
        return parameter.getValue();
    }
}
