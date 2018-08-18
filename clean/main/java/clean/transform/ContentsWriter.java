package clean.transform;

import clean.graph.EdgeDirection;
import clean.graph.EdgeType;
import clean.graph.Graph;
import clean.graph.PropertyKeys;
import clean.graph.Vertex;
import clean.graph.VertexType;
import clean.graph.match.EdgeMatcher;
import clean.graph.match.SubPath;
import clean.graph.match.SubPathMatcher;
import clean.graph.match.VertexMatcher;
import clean.graph.match.matchers.edge.TypeEdgeMatcher;
import clean.graph.match.matchers.vertex.TypeVertexMatcher;
import clean.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class ContentsWriter {
    private static final VertexMatcher FOLDER_MATCHER = new TypeVertexMatcher(VertexType.Folder);
    private static final SubPathMatcher FILE_MATCHER = createFileMatcher();
    private static final SubPathMatcher FIRST_CHILD_MATCHER = createFirstChildMatcher();
    private static final SubPathMatcher NEXT_SIBLING_MATCHER = createNextSiblingMatcher();

    private static SubPathMatcher createFileMatcher() {
        EdgeMatcher fileEdgeMatcher = new TypeEdgeMatcher(EdgeDirection.Outgoing, EdgeType.FolderContainsFile);
        VertexMatcher fileVertexMatcher = new TypeVertexMatcher(VertexType.File);
        return new SubPathMatcher(fileEdgeMatcher, fileVertexMatcher);
    }

    private static SubPathMatcher createFirstChildMatcher() {
        EdgeMatcher firstChildEdgeMatcher = new TypeEdgeMatcher(EdgeDirection.Outgoing, EdgeType.FirstChild);
        return new SubPathMatcher(firstChildEdgeMatcher);
    }

    private static SubPathMatcher createNextSiblingMatcher() {
        EdgeMatcher nextSiblingEdgeMatcher = new TypeEdgeMatcher(EdgeDirection.Outgoing, EdgeType.NextSibling);
        return new SubPathMatcher(nextSiblingEdgeMatcher);
    }

    public Map<String, Map<String, String>> writeToFolderFileContents(Graph graph) {
        Map<String, Map<String, String>> folderFileContents = new HashMap<>();
        FOLDER_MATCHER.apply(graph)
                .forEach(folderVertex -> folderFileContents.put(
                        folderVertex.getProperty(PropertyKeys.FOLDER_NAME),
                        writeFolderToFileContents(graph, folderVertex)
                        )
                );
        return folderFileContents;
    }

    private Map<String, String> writeFolderToFileContents(Graph graph, Vertex folderVertex) {
        Map<String, String> fileContents = new HashMap<>();
        FILE_MATCHER.apply(graph, folderVertex)
                .map(SubPath::getVertex)
                .forEach(fileVertex -> fileContents.put(
                        fileVertex.getProperty(PropertyKeys.FILE_NAME),
                        createFileContents(graph, fileVertex)
                ));
        return fileContents;
    }

    private String createFileContents(Graph graph, Vertex fileVertex) {
        StringBuilder sb = new StringBuilder();
        appendVertexContents(graph, fileVertex, sb);
        appendHiddenText(fileVertex, sb);
        return sb.toString();
    }

    private void appendVertexContents(Graph graph, Vertex vertex, StringBuilder sb) {
        appendHiddenText(vertex, sb);
        FIRST_CHILD_MATCHER.apply(graph, vertex)
                .map(SubPath::getVertex)
                .forEach(firstChildVertex -> {
                    appendVertexContents(graph, vertex, sb);
                    NEXT_SIBLING_MATCHER.apply(graph, vertex)
                            .map(SubPath::getVertex)
                            .forEach(nextSibling -> {
                                appendVertexContents(graph, nextSibling, sb);
                            });
                });
    }

    private void appendHiddenText(Vertex vertex, StringBuilder sb) {
        String hiddenText = vertex.getProperty(PropertyKeys.HIDDEN_TEXT);
        if (StringUtil.isNotEmpty(hiddenText)) {
            sb.append(hiddenText);
        }
    }
}
