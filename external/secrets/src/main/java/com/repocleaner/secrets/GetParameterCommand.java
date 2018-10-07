package com.repocleaner.secrets;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.Parameter;
import com.repocleaner.util.Constants;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetParameterCommand {
    private final String parameterName;

    public String getParameter() {
        AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard()
                .withRegion(Constants.AWS_REGION)
                .build();
        GetParameterRequest request = new GetParameterRequest();
        request.withName(parameterName);
        request.setWithDecryption(true);
        GetParameterResult result = ssm.getParameter(request);
        Parameter parameter = result.getParameter();
        return parameter.getValue();
    }
}
