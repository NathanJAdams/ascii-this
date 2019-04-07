package com.repocleaner.coreclean.graph.match.matchers.vertex;

import com.repocleaner.coreclean.graph.PropertyKeys;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.coreclean.graph.VertexType;
import com.repocleaner.coreclean.graph.match.VertexMatcher;

public class SourceTypeVertexMatcher implements VertexMatcher {
    private final String captureId;
    private final VertexType sourceVertexType;
    private final String sourceType;

    public SourceTypeVertexMatcher(VertexType sourceVertexType, String sourceType) {
        this(null, sourceVertexType, sourceType);
    }

    public SourceTypeVertexMatcher(String captureId, VertexType sourceVertexType, String sourceType) {
        this.captureId = captureId;
        this.sourceVertexType = sourceVertexType;
        this.sourceType = sourceType;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public final boolean isMatch(Vertex vertex) {
        return vertex.isPropertyEquals(PropertyKeys.TYPE, sourceVertexType)
                && vertex.isPropertyEquals(PropertyKeys.SOURCE_TYPE, sourceType);
    }
}
