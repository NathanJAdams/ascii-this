package com.repocleaner.clean.language;

import com.repocleaner.clean.graph.Graph;
import com.repocleaner.util.RepoCleanerException;

import java.util.HashMap;
import java.util.Map;

public class GraphsBuilder {
    private final Map<Language, GraphBuilder> builders = new HashMap<>();

    public void addFile(String filePath) throws RepoCleanerException {
        Language language = Language.fromFilePath(filePath);
        if (language != null) {
            GraphBuilder builder = builders.get(language);
            if (builder == null) {
                builder = new GraphBuilder(language.getParsedFileCreator());
                builders.put(language, builder);
            }
            builder.addFile(filePath);
        }
    }

    public Map<Language, Graph> build() {
        Map<Language, Graph> graphs = new HashMap<>();
        for (Map.Entry<Language, GraphBuilder> entry : builders.entrySet()) {
            Language language = entry.getKey();
            GraphBuilder graphBuilder = entry.getValue();
            Graph graph = graphBuilder.build();
            language.getContextualiser().addContext(graph);
            graphs.put(language, graph);
        }
        return graphs;
    }
}
