package clean.transform.commands;

import clean.graph.Edge;
import clean.graph.Graph;
import clean.graph.Vertex;
import clean.transform.TransformCommand;

public class AddEdgeTransformCommand implements TransformCommand {
    private final Vertex sourceVertex;
    private final Vertex targetVertex;
    private final Edge edge;

    public AddEdgeTransformCommand(Vertex sourceVertex, Vertex targetVertex, Edge edge) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.edge = edge;
    }

    @Override
    public void execute(Graph graph) {
        graph.addEdge(sourceVertex, targetVertex, edge);
    }

    @Override
    public void undo(Graph graph) {
        graph.removeEdge(edge);
    }
}
