package com.repocleaner.secrets;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.repocleaner.util.Constants;

import java.nio.ByteBuffer;

public class SecretRetriever {

    public static String getSecretAsString(String secretName) {
        GetSecretValueResult result = getSecret(secretName);
        return (result == null)
                ? null
                : result.getSecretString();
    }

    public static ByteBuffer getSecretAsBytes(String secretName) {
        GetSecretValueResult result = getSecret(secretName);
        return (result == null)
                ? null
                : result.getSecretBinary();
    }

    private static GetSecretValueResult getSecret(String secretName) {
        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(Constants.SECRETS_MANAGER_ENDPOINT, Constants.AWS_REGION);
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        AWSSecretsManager client = clientBuilder.build();
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId(secretName);
        return client.getSecretValue(request);
    }
}
