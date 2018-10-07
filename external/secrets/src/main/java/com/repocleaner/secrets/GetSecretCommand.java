package com.repocleaner.secrets;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.repocleaner.util.Constants;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetSecretCommand {
    private final String secretName;

    public GetSecretValueResult getSecret() {
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        clientBuilder.withRegion(Constants.AWS_REGION);
        AWSSecretsManager client = clientBuilder.build();
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId(secretName);
        return client.getSecretValue(request);
    }
}
