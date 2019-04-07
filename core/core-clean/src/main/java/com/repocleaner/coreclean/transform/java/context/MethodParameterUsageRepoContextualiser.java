package com.repocleaner.coreclean.transform.java.context;

import com.repocleaner.coreclean.graph.EdgeDirection;
import com.repocleaner.coreclean.graph.EdgeType;
import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.coreclean.graph.match.CaptureGroup;
import com.repocleaner.coreclean.graph.match.EdgeMatcher;
import com.repocleaner.coreclean.graph.match.PathMatcher;
import com.repocleaner.coreclean.graph.match.SubPathMatcher;
import com.repocleaner.coreclean.graph.match.VertexMatcher;
import com.repocleaner.coreclean.graph.match.matchers.edge.TypeEdgeMatcher;
import com.repocleaner.coreclean.graph.match.matchers.vertex.AnyVertexMatcher;
import com.repocleaner.coreclean.graph.match.matchers.vertex.BranchSourceTypeVertexMatcher;
import com.repocleaner.coreclean.languages.java.parser.JavaParserRulesParameters;
import com.repocleaner.coreclean.transform.Contextualiser;

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
        VertexMatcher formalParameterList = new BranchSourceTypeVertexMatcher("formal-parameters", JavaParserRulesParameters.FormalParameters.name());
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
