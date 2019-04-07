package com.repocleaner.apigateway.beans;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypeEnumAwsModel implements TypeAwsModel {
    private final String type = "string";
    @SerializedName("enum")
    private final List<String> enumElements;
}
