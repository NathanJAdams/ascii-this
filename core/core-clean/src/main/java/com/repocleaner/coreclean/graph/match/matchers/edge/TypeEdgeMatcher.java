package com.repocleaner.coreclean.graph.match.matchers.edge;

import com.repocleaner.coreclean.graph.Edge;
import com.repocleaner.coreclean.graph.EdgeDirection;
import com.repocleaner.coreclean.graph.EdgeType;
import com.repocleaner.coreclean.graph.PropertyKeys;
import com.repocleaner.coreclean.graph.match.EdgeMatcher;

public class TypeEdgeMatcher implements EdgeMatcher {
    private final String captureId;
    private final EdgeDirection edgeDirection;
    private final EdgeType edgeType;

    public TypeEdgeMatcher(EdgeDirection edgeDirection, EdgeType edgeType) {
        this(null, edgeDirection, edgeType);
    }

    public TypeEdgeMatcher(String captureId, EdgeDirection edgeDirection, EdgeType edgeType) {
        this.captureId = captureId;
        this.edgeDirection = edgeDirection;
        this.edgeType = edgeType;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    public EdgeDirection getEdgeDirection() {
        return edgeDirection;
    }

    public EdgeType getEdgeType() {
        return edgeType;
    }

    @Override
    public boolean isMatch(Edge edge) {
        return edge.isPropertyEquals(PropertyKeys.TYPE, edgeType);
    }
}
