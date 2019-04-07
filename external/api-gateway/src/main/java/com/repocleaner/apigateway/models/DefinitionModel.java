package com.repocleaner.apigateway.models;

import com.repocleaner.apigateway.Model;
import com.repocleaner.apigateway.beans.TypeAwsModel;
import com.repocleaner.apigateway.beans.TypeClassAwsModel;
import com.repocleaner.apigateway.beans.TypeEnumAwsModel;
import com.repocleaner.apigateway.beans.TypePrimitiveAwsModel;
import com.repocleaner.apigateway.beans.TypeRefAwsModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefinitionModel implements Model<TypeAwsModel> {
    @Getter
    private final Class<?> schemaClass;

    @Override
    public TypeAwsModel convert() {
        if (JsonTypeNames.isPrimitiveJsonType(schemaClass)) {
            return convertPrimitive();
        } else if (schemaClass.isEnum()) {
            return convertEnum();
        } else {
            return convertClass();
        }
    }

    private TypeAwsModel convertPrimitive() {
        String type = JsonTypeNames.getTypeName(schemaClass);
        return new TypePrimitiveAwsModel(type);
    }

    private TypeAwsModel convertEnum() {
        List<String> enumElements = new ArrayList<>();
        for (Object enumElement : schemaClass.getEnumConstants()) {
            enumElements.add(enumElement.toString());
        }
        return new TypeEnumAwsModel(enumElements);

    }

    private TypeAwsModel convertClass() {
        Field[] fields = schemaClass.getDeclaredFields();
        Map<String, TypeRefAwsModel> properties = null;
        List<String> required = null;
        if (fields.length > 0) {
            properties = new HashMap<>();
            required = new ArrayList<>();
            for (Field field : fields) {
                String name = field.getName();
                TypeRefAwsModel fieldModel = TypeRefConverter.convertClass(field.getType());
                properties.put(name, fieldModel);
                required.add(name);
            }
        }
        return new TypeClassAwsModel(properties, required);
    }
}
