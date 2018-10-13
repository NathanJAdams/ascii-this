package com.repocleaner.coreclean.graph.match;

import com.repocleaner.coreclean.graph.GraphElement;

public interface GraphElementMatcher<T extends GraphElement> {
    String getCaptureId();

    boolean isMatch(T graphElement);
}
