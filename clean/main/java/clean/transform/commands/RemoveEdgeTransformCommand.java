package clean.transform.commands;

import clean.graph.Edge;
import clean.graph.Graph;
import clean.graph.Vertex;
import clean.transform.TransformCommand;

public class RemoveEdgeTransformCommand implements TransformCommand {
    private final Vertex sourceVertex;
    private final Vertex targetVertex;
    private final Edge edge;

    public RemoveEdgeTransformCommand(Vertex sourceVertex, Vertex targetVertex, Edge edge) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.edge = edge;
    }

    @Override
    public void execute(Graph graph) {
        graph.removeEdge(edge);
    }

    @Override
    public void undo(Graph graph) {
        graph.addEdge(sourceVertex, targetVertex, edge);
    }
}
