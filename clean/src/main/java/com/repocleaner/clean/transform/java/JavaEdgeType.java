package com.repocleaner.clean.transform.java;

import com.repocleaner.clean.graph.GraphElementType;

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
