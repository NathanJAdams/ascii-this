package com.repocleaner.clean;

import com.repocleaner.clean.graph.Graph;
import com.repocleaner.clean.language.GraphWriter;
import com.repocleaner.clean.language.GraphsBuilder;
import com.repocleaner.clean.language.Language;
import com.repocleaner.clean.transform.Transformation;
import com.repocleaner.clean.transform.TransformationCoster;
import com.repocleaner.clean.transform.Transformer;
import com.repocleaner.clean.transform.coster.PlainCoster;
import com.repocleaner.clean.transform.transformers.EOFTransformer;
import com.repocleaner.userinfo.config.UserConfig;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cleaner {
    public static void clean(File requestFolder) throws RepoCleanerException {
        Git git = source.saveCodeTo(sourceFolder);
        String cleanBranch = source.createCleanBranchName(git);
        GitUtil.checkoutNewBranch(git, cleanBranch);
        int tokenCost = clean(sourceFolder, user);
        if (tokenCost == 0) {
            throw new RepoCleanerException("No changes made");
        }
        if (user.countTokens() < tokenCost) {
            throw new RepoCleanerException("Insufficient tokens");
        }
        String title = "Cleaned by cleaner.com @ " + FORMATTER.format(LocalDateTime.now());
        String description = "Tokens consumed: " + tokenCost;
        source.sendCleanedCode(git, cleanBranch, title, description);
        user.registerCleanEvent(tokenCost);
    }

    private static int clean(File sourceFolder, UserConfig config) throws RepoCleanerException {
        GraphsBuilder graphsBuilder = new GraphsBuilder();
        Path sourceFolderPath = Paths.get(sourceFolder.getAbsolutePath());
        try {
            List<String> fileNames = Files.walk(sourceFolderPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .map(File::getAbsolutePath)
                    .collect(Collectors.toList());
            for (String fileName : fileNames) {
                graphsBuilder.addFile(fileName);
            }
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to com.repocleaner.clean repository", e);
        }
        Map<Language, Graph> graphs = graphsBuilder.build();
        TransformationCoster coster = new PlainCoster();
        Transformer transformer = new EOFTransformer("// EOF");
        int totalCost = 0;
        int maxTokens = config.getMaxTokens();
        GraphWriter graphWriter = new GraphWriter();
        for (Graph graph : graphs.values()) {
            Transformation transformation = transformer.createTransformation(graph);
            int cost = coster.calculateTokenCost(transformation);
            int newTotalCost = totalCost + cost;
            if (newTotalCost > maxTokens) {
                return totalCost;
            }
            totalCost = newTotalCost;
            transformation.getCommands().forEach(command -> command.execute(graph));
            graphWriter.write(graph);
        }
        return totalCost;
    }
}
