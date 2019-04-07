package com.repocleaner.apigateway.beans;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InfoAwsModel {
    private final LocalDateTime version;
    private final String title;
}
