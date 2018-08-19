package com.repocleaner.clean.unused;

import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.transform.SuccessStrategy;
import com.repocleaner.clean.transform.Transformation;
import com.repocleaner.clean.transform.Transformer;

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
