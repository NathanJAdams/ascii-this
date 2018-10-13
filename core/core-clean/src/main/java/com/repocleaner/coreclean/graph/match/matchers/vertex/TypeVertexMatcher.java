package com.repocleaner.coreclean.graph.match.matchers.vertex;

import com.repocleaner.coreclean.graph.GraphElementType;
import com.repocleaner.coreclean.graph.PropertyKeys;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.coreclean.graph.match.VertexMatcher;

public class TypeVertexMatcher implements VertexMatcher {
    private final String captureId;
    private final GraphElementType type;

    public TypeVertexMatcher(GraphElementType type) {
        this(null, type);
    }

    public TypeVertexMatcher(String captureId, GraphElementType type) {
        this.captureId = captureId;
        this.type = type;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public boolean isMatch(Vertex vertex) {
        return vertex.isPropertyEquals(PropertyKeys.TYPE, type);
    }
}
