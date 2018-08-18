package clean.transform;

import clean.graph.Graph;

public interface Transformer {
    SuccessStrategy getSuccessStrategy();

    Transformation createTransformation(Graph graph);
}
