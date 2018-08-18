package clean.graph.match.matchers.vertex;

import clean.graph.PropertyKey;
import clean.graph.Vertex;
import clean.graph.match.VertexMatcher;

import java.util.Objects;

public class PropertyEqualsVertexMatcher<P> implements VertexMatcher {
    private final String captureId;
    private final PropertyKey<P> key;
    private final P property;

    public PropertyEqualsVertexMatcher(PropertyKey<P> key, P property) {
        this(null, key, property);
    }

    public PropertyEqualsVertexMatcher(String captureId, PropertyKey<P> key, P property) {
        this.captureId = captureId;
        this.key = key;
        this.property = property;
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public boolean isMatch(Vertex vertex) {
        P actualProperty = vertex.getProperty(key);
        return Objects.equals(actualProperty, property);
    }
}
