package com.repocleaner.apigateway.models;

import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.beans.InfoAwsModel;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InfoModel implements Model<InfoAwsModel> {
    private final LocalDateTime version;
    private final String title;

    @Override
    public InfoAwsModel convert() {
        return new InfoAwsModel(version, title);
    }
}
