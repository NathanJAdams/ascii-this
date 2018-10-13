package com.repocleaner.coreclean.transform;

import com.repocleaner.coreclean.graph.Graph;

public interface Transformer {
    SuccessStrategy getSuccessStrategy();

    Transformation createTransformation(Graph graph);
}
