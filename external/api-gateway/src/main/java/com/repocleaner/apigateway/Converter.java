package com.repocleaner.apigateway;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.repocleaner.apigateway.beans.RestApiAwsModel;
import com.repocleaner.apigateway.deployments.EncryptTokenDeployment;
import com.repocleaner.apigateway.deployments.RequestWebDeployment;
import com.repocleaner.apigateway.deployments.RetrieveDiffDeployment;
import com.repocleaner.apigateway.models.RestApiModel;
import java.time.LocalDateTime;

public class Converter {
    private static Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeConverter())
            .create();

    public static void main(String[] args) {
//        RestApiModel restApi = new EncryptTokenDeployment().createRestApiModel();
//        RestApiModel restApi = new RequestWebDeployment().createRestApiModel();
        RestApiModel restApi = new RetrieveDiffDeployment().createRestApiModel();
        RestApiAwsModel restApiAwsModel = restApi.convert();
        String json = GSON.toJson(restApiAwsModel);
        System.out.println(json);
    }
}
