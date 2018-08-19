package com.repocleaner.clean.graph.match.matchers.vertex;

import com.repocleaner.clean.graph.PropertyKeys;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.graph.VertexType;
import com.repocleaner.clean.graph.match.VertexMatcher;

public class SourceTypeVertexMatcher implements VertexMatcher {
    private final String captureId;
    private final VertexType sourceVertexType;
    private final int sourceType;

    public SourceTypeVertexMatcher(VertexType sourceVertexType, int sourceType) {
        this(null, sourceVertexType, sourceType);
    }

    public SourceTypeVertexMatcher(String captureId, VertexType sourceVertexType, int sourceType) {
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
