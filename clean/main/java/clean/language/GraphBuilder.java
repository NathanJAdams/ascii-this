package clean.language;

import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import clean.antlr.HiddenTokensListener;
import clean.graph.Edge;
import clean.graph.EdgeType;
import clean.graph.Graph;
import clean.graph.PropertyKeys;
import clean.graph.Vertex;
import clean.util.RepoCleanerException;
import clean.util.StringUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GraphBuilder {
    private final ParsedFileCreator parsedFileCreator;
    private final Graph graph = new Graph();
    private final Vertex repoVertex = Vertex.createRepoVertex();
    private final Map<String, Vertex> folderVertices = new HashMap<>();

    public GraphBuilder(ParsedFileCreator parsedFileCreator) {
        this.parsedFileCreator = parsedFileCreator;
        graph.addVertex(repoVertex);
    }

    public Graph build() {
        return graph;
    }

    public void addFile(String filePath) throws RepoCleanerException {
        ParsedFile parsedFile = parsedFileCreator.parse(filePath);
        if (parsedFile == null) {
            throw new RepoCleanerException("Couldn't parse file: " + filePath);
        }
        ParseTree fileParseTree = parsedFile.getParseTree();
        Map<ParseTree, Vertex> mapping = new HashMap<>();
        addToMapping(mapping, fileParseTree);
        mapping.values().forEach(graph::addVertex);
        Vertex contentsVertex = mapping.get(fileParseTree);
        addFileEdges(graph, repoVertex, folderVertices, filePath, contentsVertex);
        addRecursiveEdges(graph, fileParseTree, mapping);
        addHiddenText(mapping, parsedFile);
    }

    private void addToMapping(Map<ParseTree, Vertex> mapping, ParseTree parseTree) throws RepoCleanerException {
        Vertex vertex = toVertex(parseTree);
        if (vertex != null) {
            mapping.put(parseTree, vertex);
            int childCount = parseTree.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ParseTree child = parseTree.getChild(i);
                addToMapping(mapping, child);
            }
        }
    }

    private Vertex toVertex(ParseTree parseTree) throws RepoCleanerException {
        if (parseTree instanceof RuleNode) {
            int ruleIndex = ((RuleNode) parseTree).getRuleContext().getRuleIndex();
            return Vertex.createRuleVertex(ruleIndex);
        } else if (parseTree instanceof TerminalNode) {
            Token symbol = ((TerminalNode) parseTree).getSymbol();
            int type = symbol.getType();
            String text = (type == Recognizer.EOF)
                    ? null
                    : symbol.getText();
            return Vertex.createNodeVertex(type, text);
        } else {
            throw new RepoCleanerException("Failed to clean");
        }
    }

    private void addFileEdges(Graph graph, Vertex repoVertex, Map<String, Vertex> folderVertices, String filePath, Vertex contentsVertex) {
        Vertex fileVertex = Vertex.createFileVertex(filePath);
        graph.addVertex(fileVertex);
        graph.addEdge(repoVertex, fileVertex, new Edge(EdgeType.RepoContainsFile));
        int lastSeparatorIndex = filePath.lastIndexOf(File.separatorChar);
        if (lastSeparatorIndex != -1) {
            String folderName = filePath.substring(0, lastSeparatorIndex);
            Vertex folderVertex = folderVertices.get(folderName);
            if (folderVertex == null) {
                folderVertex = Vertex.createFolderVertex(folderName);
                graph.addVertex(folderVertex);
                graph.addEdge(repoVertex, folderVertex, new Edge(EdgeType.RepoContainsFolder));
                folderVertices.put(folderName, folderVertex);
            }
            graph.addEdge(folderVertex, fileVertex, new Edge(EdgeType.FolderContainsFile));
        }
        graph.addEdge(fileVertex, contentsVertex, new Edge(EdgeType.FirstChild));
    }

    private void addRecursiveEdges(Graph graph, ParseTree parseTree, Map<ParseTree, Vertex> mapping) {
        Vertex vertex = mapping.get(parseTree);
        int childCount = parseTree.getChildCount();
        if (childCount > 0) {
            ParseTree firstChild = parseTree.getChild(0);
            Vertex childVertex = mapping.get(firstChild);
            graph.addEdge(vertex, childVertex, new Edge(EdgeType.FirstChild));
            graph.addEdge(vertex, childVertex, new Edge(EdgeType.Child));
            addRecursiveEdges(graph, firstChild, mapping);
            for (int i = 1; i < childCount; i++) {
                ParseTree nextSibling = parseTree.getChild(i);
                Vertex nextSiblingVertex = mapping.get(nextSibling);
                graph.addEdge(vertex, nextSiblingVertex, new Edge(EdgeType.Child));
                graph.addEdge(childVertex, nextSiblingVertex, new Edge(EdgeType.NextSibling));
                addRecursiveEdges(graph, nextSibling, mapping);
                childVertex = nextSiblingVertex;
            }
        }
    }

    private void addHiddenText(Map<ParseTree, Vertex> mapping, ParsedFile parsedFile) {
        HiddenTokensListener hiddenTokensListener = parsedFile.getHiddenTokensListener();
        mapping.forEach((parseTree, vertex) -> {
            if (parseTree instanceof TerminalNode) {
                String hiddenText = hiddenTokensListener.getTerminalPreviousHiddenText((TerminalNode) parseTree);
                addHiddenTextVertex(vertex, hiddenText);
            }
        });
    }

    private void addHiddenTextVertex(Vertex vertex, String hiddenText) {
        if (StringUtil.isNotEmpty(hiddenText)) {
            vertex.setProperty(PropertyKeys.HIDDEN_TEXT, hiddenText);
        }
    }
}
