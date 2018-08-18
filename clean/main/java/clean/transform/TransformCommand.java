package clean.transform;

import clean.graph.Graph;

public interface TransformCommand {
    void execute(Graph graph);

    void undo(Graph graph);
}
