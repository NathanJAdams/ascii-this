package com.repocleaner.coreclean.graph.match.matchers.edge;

import com.repocleaner.coreclean.graph.Edge;
import com.repocleaner.coreclean.graph.EdgeDirection;
import com.repocleaner.coreclean.graph.match.EdgeMatcher;

public class AnyEdgeMatcher implements EdgeMatcher {
    private final String captureId;

    public AnyEdgeMatcher() {
        this(null);
    }

    public AnyEdgeMatcher(String captureId) {
        this.captureId = captureId;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public EdgeDirection getEdgeDirection() {
        return EdgeDirection.Both;
    }

    @Override
    public boolean isMatch(Edge edge) {
        return true;
    }
}
