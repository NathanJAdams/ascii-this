package clean.graph.match.matchers.vertex;

import clean.graph.VertexType;

public class SourceRuleTypeVertexMatcher extends SourceTypeVertexMatcher {
    public SourceRuleTypeVertexMatcher(int ruleSourceType) {
        super(VertexType.SourceRule, ruleSourceType);
    }

    public SourceRuleTypeVertexMatcher(String captureId, int ruleSourceType) {
        super(captureId, VertexType.SourceRule, ruleSourceType);
    }
}
