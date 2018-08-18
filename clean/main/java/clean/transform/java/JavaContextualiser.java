package clean.transform.java;

import clean.graph.Graph;
import clean.transform.Contextualiser;
import clean.transform.java.context.MethodParameterUsageRepoContextualiser;

import java.util.Arrays;
import java.util.List;

public class JavaContextualiser implements Contextualiser {
    private final List<Contextualiser> contextualisers = Arrays.asList(
            new MethodParameterUsageRepoContextualiser()
    );

    @Override
    public void addContext(Graph graph) {
        contextualisers.forEach(contextualiser -> contextualiser.addContext(graph));
    }
}
