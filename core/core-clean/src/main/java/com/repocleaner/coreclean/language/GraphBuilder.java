package com.repocleaner.coreclean.language;

import com.repocleaner.coreclean.graph.Edge;
import com.repocleaner.coreclean.graph.EdgeType;
import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.graph.PropertyKey;
import com.repocleaner.coreclean.graph.PropertyKeys;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.parser_gen.Grammar;
import com.repocleaner.parser_gen.ImmutableParseTreeFactory;
import com.repocleaner.parser_gen.ImmutableTreeBranch;
import com.repocleaner.parser_gen.ImmutableTreeLeaf;
import com.repocleaner.parser_gen.ImmutableTreeNode;
import com.repocleaner.parser_gen.LexedToken;
import com.repocleaner.parser_gen.ParserException;
import com.repocleaner.parser_gen.ParserRule;
import com.repocleaner.parser_gen.streams.CharStream;
import com.repocleaner.parser_gen.streams.CharStreamReader;
import com.repocleaner.parser_gen.streams.FileCharStream;
import com.repocleaner.util.RepoCleanerException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {
    private final ImmutableParseTreeFactory factory = new ImmutableParseTreeFactory();
    private final Grammar grammar;
    private final ParserRule fileParserRule;
    private final Graph graph = new Graph();
    private final Vertex repoVertex = Vertex.createRepoVertex();
    private final Map<String, Vertex> folderVertices = new HashMap<>();

    public GraphBuilder(Grammar grammar, ParserRule fileParserRule) {
        this.grammar = grammar;
        this.fileParserRule = fileParserRule;
        graph.addVertex(repoVertex);
    }

    public Graph build() {
        return graph;
    }

    public void addFile(String filePath) throws RepoCleanerException {
        File file = new File(filePath);
        CharStream fileCharStream = new FileCharStream(file);
        CharStreamReader reader = new CharStreamReader(fileCharStream);
        ImmutableTreeNode parsed;
        try {
            parsed = grammar.parse(reader, fileParserRule, factory);
        } catch (ParserException e) {
            throw new RepoCleanerException("Failed to parse from file: " + filePath, e);
        }
        Map<ImmutableTreeNode, Vertex> mapping = new HashMap<>();
        System.out.println("Add to mapping");
        addToMapping(mapping, parsed);
        System.out.println("Adding vertices");
        mapping.values().forEach(graph::addVertex);
        System.out.println("Getting contents vertex");
        Vertex contentsVertex = mapping.get(parsed);
        System.out.println("Adding file edges");
        addFileEdges(graph, repoVertex, folderVertices, filePath, contentsVertex);
        System.out.println("Adding recursive edges");
        addRecursiveEdges(graph, mapping, parsed);
        System.out.println("Adding hidden text");
        addHiddenText(mapping, parsed);
    }

    private void addToMapping(Map<ImmutableTreeNode, Vertex> mapping, ImmutableTreeNode node) throws RepoCleanerException {
        Vertex vertex = toVertex(node);
        if (vertex != null) {
            mapping.put(node, vertex);
            if (node instanceof ImmutableTreeBranch) {
                ImmutableTreeBranch branch = (ImmutableTreeBranch) node;
                List<ImmutableTreeNode> children = branch.getChildren();
                for (ImmutableTreeNode child : children) {
                    addToMapping(mapping, child);
                }
            }
        }
    }

    private Vertex toVertex(ImmutableTreeNode node) throws RepoCleanerException {
        String type = node.getName();
        if (node instanceof ImmutableTreeBranch) {
            return Vertex.createRuleVertex(type);
        } else if (node instanceof ImmutableTreeLeaf) {
            String token = ((ImmutableTreeLeaf) node).getParsedToken().getLexedToken().getToken();
            return Vertex.createNodeVertex(type, token);
        } else {
            throw new RepoCleanerException("Failed to com.repocleaner.coreclean");
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

    private void addRecursiveEdges(Graph graph, Map<ImmutableTreeNode, Vertex> mapping, ImmutableTreeNode node) {
        Vertex vertex = mapping.get(node);
        if (node instanceof ImmutableTreeBranch) {
            ImmutableTreeBranch branch = (ImmutableTreeBranch) node;
            List<ImmutableTreeNode> children = branch.getChildren();
            if (!children.isEmpty()) {
                ImmutableTreeNode firstChild = children.get(0);
                Vertex childVertex = mapping.get(firstChild);
                graph.addEdge(vertex, childVertex, new Edge(EdgeType.FirstChild));
                graph.addEdge(vertex, childVertex, new Edge(EdgeType.Child));
                addRecursiveEdges(graph, mapping, firstChild);
                for (int i = 1; i < children.size(); i++) {
                    ImmutableTreeNode nextSibling = children.get(i);
                    Vertex nextSiblingVertex = mapping.get(nextSibling);
                    graph.addEdge(vertex, nextSiblingVertex, new Edge(EdgeType.Child));
                    graph.addEdge(childVertex, nextSiblingVertex, new Edge(EdgeType.NextSibling));
                    addRecursiveEdges(graph, mapping, nextSibling);
                    childVertex = nextSiblingVertex;
                }
            }
        }
    }

    private void addHiddenText(Map<ImmutableTreeNode, Vertex> mapping, ImmutableTreeNode node) {
        Vertex vertex = mapping.get(node);
        if (node instanceof ImmutableTreeLeaf) {
            ImmutableTreeLeaf leaf = (ImmutableTreeLeaf) node;
            addHiddenTextVertex(vertex, leaf);
        } else if (node instanceof ImmutableTreeBranch) {
            ImmutableTreeBranch branch = (ImmutableTreeBranch) node;
            for (ImmutableTreeNode child : branch.getChildren()) {
                addHiddenText(mapping, child);
            }
        }
    }

    private void addHiddenTextVertex(Vertex vertex, ImmutableTreeLeaf leaf) {
        addTextProperty(vertex, PropertyKeys.PREVIOUS_HIDDEN_TEXT, leaf.getParsedToken().getPreviousHiddenTokens());
        addTextProperty(vertex, PropertyKeys.NEXT_HIDDEN_TEXT, leaf.getParsedToken().getNextHiddenTokens());
    }

    private void addTextProperty(Vertex vertex, PropertyKey<String> propertyKey, List<LexedToken> lexedTokens) {
        StringBuilder sb = new StringBuilder();
        for (LexedToken lexedToken : lexedTokens) {
            sb.append(lexedToken.getToken());
        }
        if (sb.length() >= 0) {
            vertex.setProperty(propertyKey, sb.toString());
        }
    }
}
