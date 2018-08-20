package com.repocleaner.clean.graph.match;

import com.repocleaner.clean.graph.GraphElement;

public interface GraphElementMatcher<T extends GraphElement> {
    String getCaptureId();

    boolean isMatch(T graphElement);
}
