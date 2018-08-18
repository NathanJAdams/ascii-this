package clean.unused;

import clean.graph.Graph;
import clean.graph.GraphElementType;
import clean.graph.PropertyKeys;
import clean.graph.Vertex;
import clean.graph.VertexType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class SourceControlFlow {
    private final Consumers ruleConsumers = new Consumers();
    private final Consumers nodeConsumers = new Consumers();

    public void addRuleControl(int ruleId, BiConsumer<Graph, Vertex> ruleConsumer) {
        ruleConsumers.addControl(ruleId, ruleConsumer);
    }

    public void addNodeControl(int nodeId, BiConsumer<Graph, Vertex> nodeConsumer) {
        nodeConsumers.addControl(nodeId, nodeConsumer);
    }

    public void perform(Graph graph, Vertex vertex) {
        GraphElementType type = vertex.getProperty(PropertyKeys.TYPE);
        Integer sourceType = vertex.getProperty(PropertyKeys.SOURCE_TYPE);
        if ((type == null) || (sourceType == null)) {
            System.out.println("Houston we have a problem");
            return;
        }
        Consumers consumers = (type == VertexType.SourceRule)
                ? ruleConsumers
                : nodeConsumers;
        consumers.perform(sourceType, graph, vertex);
    }

    private static class Consumers {
        private final Map<Integer, BiConsumer<Graph, Vertex>> consumers = new HashMap<>();

        public void addControl(int id, BiConsumer<Graph, Vertex> consumer) {
            consumers.put(id, consumer);
        }

        public void perform(int id, Graph graph, Vertex vertex) {
            BiConsumer<Graph, Vertex> consumer = consumers.get(id);
            if (consumer != null) {
                consumer.accept(graph, vertex);
            }
        }
    }
}
