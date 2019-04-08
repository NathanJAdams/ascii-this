package com.repocleaner.coreclean.language;

import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.util.RepoCleanerException;
import java.util.HashMap;
import java.util.Map;

public class GraphsBuilder {
    private final Map<Language, GraphBuilder> builders = new HashMap<>();

    public void addFile(String filePath) throws RepoCleanerException {
        System.out.println("adding file " + filePath);
        Language language = Language.fromFilePath(filePath);
        System.out.println("Parsed language " + language);
        if (language != null) {
            System.out.println("Getting builder");
            GraphBuilder builder = builders.computeIfAbsent(language, key -> new GraphBuilder(language.getGrammar(), language.getFileParserRule()));
            System.out.println("Builder " + builder);
            System.out.println("Builder add file");
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
