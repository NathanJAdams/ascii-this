package com.repocleaner.coreclean.graph.match;

import com.repocleaner.coreclean.graph.Edge;
import com.repocleaner.coreclean.graph.Vertex;

import java.util.HashMap;
import java.util.Map;

public class CaptureGroup {
    private final Map<String, Vertex> capturedVertices = new HashMap<>();
    private final Map<String, Edge> capturedEdges = new HashMap<>();

    public void clear() {
        capturedVertices.clear();
        capturedEdges.clear();
    }

    public void captureVertex(String id, Vertex vertex) {
        capturedVertices.put(id, vertex);
    }

    public void captureEdge(String id, Edge edge) {
        capturedEdges.put(id, edge);
    }

    public Vertex getCapturedVertex(String id) {
        return capturedVertices.get(id);
    }

    public Edge getCapturedEdge(String id) {
        return capturedEdges.get(id);
    }

    public boolean isVertexCaptured(String id) {
        return (getCapturedVertex(id) != null);
    }

    public boolean isEdgeCaptured(String id) {
        return (getCapturedEdge(id) != null);
    }
}