package com.repocleaner.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddHostedAccountLambdaRequest {
    private final String jwt;
    private final String userEmail;
    private final String host;
    private final String userName;
}
