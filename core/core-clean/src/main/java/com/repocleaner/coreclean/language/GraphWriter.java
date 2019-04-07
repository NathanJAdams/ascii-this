package com.repocleaner.coreclean.language;

import com.repocleaner.coreclean.graph.EdgeDirection;
import com.repocleaner.coreclean.graph.EdgeType;
import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.graph.PropertyKeys;
import com.repocleaner.coreclean.graph.Vertex;
import com.repocleaner.coreclean.graph.VertexType;
import com.repocleaner.coreclean.graph.match.matchers.vertex.TypeVertexMatcher;
import com.repocleaner.util.RepoCleanerException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class GraphWriter {
    public void write(Graph graph) throws RepoCleanerException {
        List<Vertex> fileVertices = new TypeVertexMatcher(VertexType.File)
                .apply(graph)
                .collect(Collectors.toList());
        for (Vertex fileVertex : fileVertices) {
            writeFile(fileVertex, graph);
        }
    }

    private void writeFile(Vertex fileVertex, Graph graph) throws RepoCleanerException {
        String fileName = fileVertex.getProperty(PropertyKeys.FILE_NAME);
        try (FileOutputStream fos = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             OutputStreamWriter writer = new OutputStreamWriter(bos)) {
            writeNextVertices(fileVertex, graph, writer, EdgeType.FirstChild);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to save cleaned file", e);
        }
    }

    private void writeVertex(Vertex vertex, Graph graph, Writer writer) throws RepoCleanerException {
        writeVertexText(vertex, writer);
        writeNextVertices(vertex, graph, writer, EdgeType.FirstChild);
        writeNextVertices(vertex, graph, writer, EdgeType.NextSibling);
    }

    private void writeNextVertices(Vertex vertex, Graph graph, Writer writer, EdgeType edgeType) throws RepoCleanerException {
        List<Vertex> nextVertices = graph.nextVertices(vertex, EdgeDirection.Outgoing, edgeType)
                .collect(Collectors.toList());
        for (Vertex nextVertex : nextVertices) {
            writeVertex(nextVertex, graph, writer);
        }
    }

    private void writeVertexText(Vertex vertex, Writer writer) throws RepoCleanerException {
        write(writer, vertex.getProperty(PropertyKeys.PREVIOUS_HIDDEN_TEXT));
        write(writer, vertex.getProperty(PropertyKeys.SOURCE_TEXT));
    }

    private void write(Writer writer, String string) throws RepoCleanerException {
        try {
            if (string != null) {
                writer.write(string);
            }
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to write to file", e);
        }
    }
}
