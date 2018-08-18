package clean.transform.commands;

import clean.graph.Graph;
import clean.graph.Vertex;
import clean.transform.TransformCommand;

public class AddVertexTransformCommand implements TransformCommand {
    private final Vertex vertex;

    public AddVertexTransformCommand(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void execute(Graph graph) {
        graph.addVertex(vertex);
    }

    @Override
    public void undo(Graph graph) {
        graph.removeVertex(vertex);
    }
}
