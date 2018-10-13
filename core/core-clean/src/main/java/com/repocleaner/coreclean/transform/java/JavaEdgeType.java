package com.repocleaner.coreclean.transform.java;

import com.repocleaner.coreclean.graph.GraphElementType;

public enum JavaEdgeType implements GraphElementType {
    Package,
    InheritedBy,
    ReferencedBy,
    MethodParameterisedBy;

    @Override
    public String getName() {
        return name();
    }
}
