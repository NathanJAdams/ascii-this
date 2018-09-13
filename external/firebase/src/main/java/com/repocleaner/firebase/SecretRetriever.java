package com.repocleaner.firebase;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

import java.nio.ByteBuffer;

public class SecretRetriever {
    private static final String ENDPOINT = "secretsmanager.eu-west-1.amazonaws.com";
    private static final String REGION = "eu-west-1";

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

    public static GetSecretValueResult getSecret(String secretName) {
        AwsClientBuilder.EndpointConfiguration config = new AwsClientBuilder.EndpointConfiguration(ENDPOINT, REGION);
        AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(config);
        AWSSecretsManager client = clientBuilder.build();
        GetSecretValueRequest request = new GetSecretValueRequest().withSecretId(secretName);
        return client.getSecretValue(request);
    }
}
