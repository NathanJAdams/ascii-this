package com.repocleaner.clean.transform;

import com.repocleaner.clean.graph.Graph;

public interface Transformer {
    SuccessStrategy getSuccessStrategy();

    Transformation createTransformation(Graph graph);
}
