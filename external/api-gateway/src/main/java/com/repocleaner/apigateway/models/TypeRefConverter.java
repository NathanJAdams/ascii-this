package com.repocleaner.apigateway.models;

import com.repocleaner.apigateway.beans.TypeRefArrayAwsModel;
import com.repocleaner.apigateway.beans.TypeRefAwsModel;
import com.repocleaner.apigateway.beans.TypeRefClassAwsModel;
import com.repocleaner.apigateway.beans.TypeRefPrimitiveAwsModel;

public class TypeRefConverter {
    public static TypeRefAwsModel convertClass(Class<?> refClass) {
        if (JsonTypeNames.isPrimitiveJsonType(refClass)) {
            return convertFieldPrimitive(refClass);
        } else if (refClass.isArray()) {
            return convertFieldArray(refClass);
        } else {
            return convertFieldClass(refClass);
        }
    }

    public static TypeRefPrimitiveAwsModel convertFieldPrimitive(Class<?> fieldClass) {
        String type = JsonTypeNames.getTypeName(fieldClass);
        return new TypeRefPrimitiveAwsModel(type);
    }

    public static TypeRefArrayAwsModel convertFieldArray(Class<?> fieldClass) {
        Class<?> componentType = fieldClass.getComponentType();
        return new TypeRefArrayAwsModel(componentType);
    }

    public static TypeRefClassAwsModel convertFieldClass(Class<?> fieldClass) {
        return new TypeRefClassAwsModel(fieldClass);
    }
}
