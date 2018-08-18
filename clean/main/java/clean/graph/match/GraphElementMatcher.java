package clean.graph.match;

import clean.graph.GraphElement;

public interface GraphElementMatcher<T extends GraphElement> {
    String getCaptureId();

    boolean isMatch(T graphElement);
}
