package clean.transform.java;

import clean.graph.GraphElementType;

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
