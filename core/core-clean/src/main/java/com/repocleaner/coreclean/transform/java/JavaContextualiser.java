package com.repocleaner.coreclean.transform.java;

import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.transform.Contextualiser;
import java.util.Arrays;
import java.util.List;

public class JavaContextualiser implements Contextualiser {
    private final List<Contextualiser> contextualisers = Arrays.asList();

    @Override
    public void addContext(Graph graph) {
        contextualisers.forEach(contextualiser -> contextualiser.addContext(graph));
    }
}
