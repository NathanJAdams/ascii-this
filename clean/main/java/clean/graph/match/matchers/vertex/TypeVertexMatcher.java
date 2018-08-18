package clean.graph.match.matchers.vertex;

import clean.graph.GraphElementType;
import clean.graph.PropertyKeys;
import clean.graph.Vertex;
import clean.graph.match.VertexMatcher;

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
