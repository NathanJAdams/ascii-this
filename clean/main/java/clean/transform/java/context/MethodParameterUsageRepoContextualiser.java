package clean.transform.java.context;

import clean.antlr.java.gen.JavaParser;
import clean.graph.EdgeDirection;
import clean.graph.EdgeType;
import clean.graph.Graph;
import clean.graph.Vertex;
import clean.graph.match.CaptureGroup;
import clean.graph.match.EdgeMatcher;
import clean.graph.match.PathMatcher;
import clean.graph.match.SubPathMatcher;
import clean.graph.match.VertexMatcher;
import clean.graph.match.matchers.edge.TypeEdgeMatcher;
import clean.graph.match.matchers.vertex.AnyVertexMatcher;
import clean.graph.match.matchers.vertex.SourceRuleTypeVertexMatcher;
import clean.transform.Contextualiser;

public class MethodParameterUsageRepoContextualiser implements Contextualiser {
    @Override
    public void addContext(Graph graph) {
        // TODO
        // get methods
        // get parameter identifiers
        // get identifiers in the method that aren't in an inner class
        // add ReferencedBy java edge types for each identifier usage to identifier parameter
        PathMatcher pathMatcher = getPathMatcher();
        pathMatcher.apply(graph)
                .forEach(captureGroup -> addContext(graph, captureGroup));
    }

    private PathMatcher getPathMatcher() {
        VertexMatcher formalParameterList = new SourceRuleTypeVertexMatcher("formal-parameters", JavaParser.RULE_formalParameters);
        EdgeMatcher formalParametersFirstChildEdge = new TypeEdgeMatcher(EdgeDirection.Outgoing, EdgeType.FirstChild);
        VertexMatcher formalParametersFirstChild = new AnyVertexMatcher("first-child");
        SubPathMatcher firstChildMatcher = new SubPathMatcher(formalParametersFirstChildEdge, formalParametersFirstChild);
        return new PathMatcher(formalParameterList, firstChildMatcher);
    }

    private void addContext(Graph graph, CaptureGroup captureGroup) {
    }

    private void addFormalParameters(Graph graph, Vertex formalParameters) {
        Vertex lastFormalParameter = graph.firstOrNull(formalParameters, EdgeDirection.Outgoing, EdgeType.NextSibling);
        if (lastFormalParameter != null) {

        }
    }

    private void addLastFormalParameter(Graph graph, Vertex lastFormalParameter) {

    }

    private void addReceiverParameter(Graph graph, Vertex receiverParameter) {

    }
}
