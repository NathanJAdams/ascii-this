package com.repocleaner.clean.transform;

import com.repocleaner.clean.graph.Graph;

import java.util.Arrays;
import java.util.List;

public class TransformStrategy {
    private final TransformationCoster coster;
    private final List<Transformer> transformers;

    public TransformStrategy(TransformationCoster coster, Transformer... transformers) {
        this(coster, Arrays.asList(transformers));
    }

    public TransformStrategy(TransformationCoster coster, List<Transformer> transformers) {
        this.coster = coster;
        this.transformers = transformers;
    }

    public int transform(int totalTokens, Graph graph) {
        int currentTokenCost = 0;
        for (int i = 0; i < transformers.size(); i++) {
            Transformer transformer = transformers.get(i);
            Transformation transformation = transformer.createTransformation(graph);
            int tokenCost = coster.calculateTokenCost(transformation);
            int newTokenCost = currentTokenCost + tokenCost;
            if (totalTokens >= newTokenCost) {
                currentTokenCost += tokenCost;
                transformation.getCommands()
                        .forEach(command -> command.execute(graph));
                i = transformer.getSuccessStrategy().calculateNextIndex(i, transformers.size());
            }
        }
        return currentTokenCost;
    }
}
