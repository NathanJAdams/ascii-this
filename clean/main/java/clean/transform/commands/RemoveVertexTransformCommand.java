package clean.transform.commands;

import clean.graph.Graph;
import clean.graph.Vertex;
import clean.transform.TransformCommand;

public class RemoveVertexTransformCommand implements TransformCommand {
    private final Vertex vertex;

    public RemoveVertexTransformCommand(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public void execute(Graph graph) {
        graph.removeVertex(vertex);
    }

    @Override
    public void undo(Graph graph) {
        graph.addVertex(vertex);
    }
}
