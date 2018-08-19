package com.repocleaner.clean.transform;

import com.repocleaner.clean.graph.Graph;

public interface TransformCommand {
    void execute(Graph graph);

    void undo(Graph graph);
}
