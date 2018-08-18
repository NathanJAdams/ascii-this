package clean.graph.match.matchers.vertex;

import clean.graph.PropertyKeys;
import clean.graph.Vertex;
import clean.graph.match.VertexMatcher;

import java.util.regex.Pattern;

public class SourceTextRegexVertexMatcher implements VertexMatcher {
    private final String captureId;
    private final Pattern pattern;

    public SourceTextRegexVertexMatcher(String textRegex) {
        this(null, textRegex);
    }

    public SourceTextRegexVertexMatcher(String captureId, String textRegex) {
        this.captureId = captureId;
        this.pattern = Pattern.compile(textRegex);
    }

    @Override
    public String getCaptureId() {
        return captureId;
    }

    @Override
    public boolean isMatch(Vertex vertex) {
        String text = vertex.getProperty(PropertyKeys.SOURCE_TEXT);
        return pattern.matcher(text).matches();
    }
}
