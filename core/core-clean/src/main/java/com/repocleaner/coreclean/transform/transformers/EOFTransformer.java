package com.repocleaner.coreclean.transform.transformers;

import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.graph.PropertyKeys;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.coreclean.graph.match.VertexMatcher;
import com.repocleaner.coreclean.graph.match.matchers.vertex.SourceNodeTypeVertexMatcher;
import com.repocleaner.coreclean.transform.SuccessStrategy;
import com.repocleaner.coreclean.transform.Transformation;
import com.repocleaner.coreclean.transform.Transformer;
import com.repocleaner.util.StringUtil;
import org.antlr.v4.runtime.Recognizer;

import java.util.List;
import java.util.stream.Collectors;

public class EOFTransformer implements Transformer {
    private final VertexMatcher EOF_MATCHER = new SourceNodeTypeVertexMatcher(Recognizer.EOF);
    private final String eofText;

    public EOFTransformer(String eofText) {
        this.eofText = eofText;
    }

    @Override
    public SuccessStrategy getSuccessStrategy() {
        return SuccessStrategy.Continue;
    }

    @Override
    public Transformation createTransformation(Graph graph) {
        Transformation.Builder builder = new Transformation.Builder();
        List<Vertex> eofVertices = EOF_MATCHER.apply(graph)
                .collect(Collectors.toList());
        if (eofVertices.isEmpty()) {
            return null;
        }
        for (Vertex eofVertex : eofVertices) {
            String previousHiddenText = eofVertex.setProperty(PropertyKeys.HIDDEN_TEXT, eofText);
            if (StringUtil.isNotEmpty(previousHiddenText)) {
                builder.change(1);
            } else {
                builder.add(1);
            }
        }
        int total = builder.getAdded() + builder.getChanged();
        if (total > 0) {
            builder.addDescriptionLine(total + " EOF markers");
        }
        return builder.build();
    }
}
