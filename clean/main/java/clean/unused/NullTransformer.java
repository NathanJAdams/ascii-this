package clean.unused;

import clean.graph.Graph;
import clean.transform.SuccessStrategy;
import clean.transform.Transformation;
import clean.transform.Transformer;

public class NullTransformer implements Transformer {
    @Override
    public SuccessStrategy getSuccessStrategy() {
        return SuccessStrategy.Continue;
    }

    @Override
    public Transformation createTransformation(Graph graph) {
        return null;
    }
}
