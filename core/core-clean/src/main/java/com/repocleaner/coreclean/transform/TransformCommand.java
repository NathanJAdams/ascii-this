package com.repocleaner.coreclean.transform;

import com.repocleaner.coreclean.graph.Graph;

public interface TransformCommand {
    void execute(Graph graph);

    void undo(Graph graph);
}
