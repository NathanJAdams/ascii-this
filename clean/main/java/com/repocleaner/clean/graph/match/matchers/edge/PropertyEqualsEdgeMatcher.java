package com.repocleaner.clean.graph.match.matchers.edge;

import com.repocleaner.clean.graph.Edge;
import com.repocleaner.clean.graph.EdgeDirection;
import com.repocleaner.clean.graph.PropertyKey;
import com.repocleaner.clean.graph.match.EdgeMatcher;

import java.util.Objects;

public class PropertyEqualsEdgeMatcher<P> implements EdgeMatcher {
    private final String captureId;
    private final EdgeDirection edgeDirection;
    private final PropertyKey<P> key;
    private final P property;

    public PropertyEqualsEdgeMatcher(EdgeDirection edgeDirection, PropertyKey<P> key, P property) {
        this(null, edgeDirection, key, property);
    }

    public PropertyEqualsEdgeMatcher(String captureId, EdgeDirection edgeDirection, PropertyKey<P> key, P property) {
        this.captureId = captureId;
        this.edgeDirection = edgeDirection;
        this.key = key;
        this.property = property;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public EdgeDirection getEdgeDirection() {
        return edgeDirection;
    }

    @Override
    public boolean isMatch(Edge edge) {
        P actualProperty = edge.getProperty(key);
        return Objects.equals(actualProperty, property);
    }
}
