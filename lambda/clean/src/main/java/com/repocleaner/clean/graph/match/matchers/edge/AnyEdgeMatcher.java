package com.repocleaner.clean.graph.match.matchers.edge;

import com.repocleaner.clean.graph.Edge;
import com.repocleaner.clean.graph.EdgeDirection;
import com.repocleaner.clean.graph.match.EdgeMatcher;

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
