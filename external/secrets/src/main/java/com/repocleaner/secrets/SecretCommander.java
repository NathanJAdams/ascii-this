package com.repocleaner.secrets;

import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

import java.nio.ByteBuffer;

public class SecretCommander {
    public static String getSecretAsString(String secretName) {
        GetSecretCommand command = new GetSecretCommand(secretName);
        GetSecretValueResult secret = command.getSecret();
        return (secret == null)
                ? null
                : secret.getSecretString();
    }

    public static ByteBuffer getSecretAsBytes(String secretName) {
        GetSecretCommand command = new GetSecretCommand(secretName);
        GetSecretValueResult secret = command.getSecret();
        return (secret == null)
                ? null
                : secret.getSecretBinary();
    }

    public static String getParameter(String parameterName) {
        GetParameterCommand command = new GetParameterCommand(parameterName);
        return command.getParameter();
    }
}
