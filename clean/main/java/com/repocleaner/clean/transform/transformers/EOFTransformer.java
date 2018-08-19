package com.repocleaner.clean.transform.transformers;

import org.antlr.v4.runtime.Recognizer;
import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.graph.PropertyKeys;
import com.repocleaner.clean.graph.Vertex;
import com.repocleaner.clean.graph.match.VertexMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.SourceNodeTypeVertexMatcher;
import com.repocleaner.clean.transform.SuccessStrategy;
import com.repocleaner.clean.transform.Transformation;
import com.repocleaner.clean.transform.Transformer;
import com.repocleaner.clean.util.StringUtil;

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
        for (Vertex eofVertex : eofVertices) {
            String previousHiddenText = eofVertex.setProperty(PropertyKeys.HIDDEN_TEXT, eofText);
            if (StringUtil.isNotEmpty(previousHiddenText)) {
                builder.change(1);
            } else {
                builder.add(1);
            }
        }
        return builder.build();
    }
}
