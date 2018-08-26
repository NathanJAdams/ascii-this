package com.repocleaner.clean.transform.java.context;

import com.repocleaner.clean.antlr.java.gen.JavaParser;
import com.repocleaner.clean.graph.EdgeDirection;
import com.repocleaner.clean.graph.EdgeType;
import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.graph.match.CaptureGroup;
import com.repocleaner.clean.graph.match.EdgeMatcher;
import com.repocleaner.clean.graph.match.PathMatcher;
import com.repocleaner.clean.graph.match.SubPathMatcher;
import com.repocleaner.clean.graph.match.VertexMatcher;
import com.repocleaner.clean.graph.match.matchers.edge.TypeEdgeMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.AnyVertexMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.SourceRuleTypeVertexMatcher;
import com.repocleaner.clean.transform.Contextualiser;

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
