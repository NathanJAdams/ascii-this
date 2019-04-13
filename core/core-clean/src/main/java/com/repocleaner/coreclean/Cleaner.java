package com.repocleaner.coreclean;

import com.repocleaner.coreclean.graph.Graph;
import com.repocleaner.coreclean.language.GraphWriter;
import com.repocleaner.coreclean.language.GraphsBuilder;
import com.repocleaner.coreclean.language.Language;
import com.repocleaner.coreclean.transform.Transformation;
import com.repocleaner.coreclean.transform.TransformationCoster;
import com.repocleaner.coreclean.transform.Transformer;
import com.repocleaner.coreclean.transform.coster.PlainCoster;
import com.repocleaner.coreclean.transform.transformers.EOFTransformer;
import com.repocleaner.io.CleanIO;
import com.repocleaner.model.CleanRequest;
import com.repocleaner.model.CleanResult;
import com.repocleaner.model.Config;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.Initiator;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jgit.api.Git;

public class Cleaner {
    public static void clean(CleanIO cleanIO) throws RepoCleanerException {
        System.out.println("Getting file structure");
        FileStructure fileStructure = cleanIO.getFileStructure();
        System.out.println("Getting lifecycle request");
        CleanRequest cleanRequest = fileStructure.getLifecycleRequest();
        System.out.println("Getting initiator");
        Initiator initiator = cleanRequest.getInitiator();
        System.out.println("Getting config");
        Config config = cleanRequest.getConfig();
        System.out.println("Getting code folder");
        File codeFolder = fileStructure.getCodeFolder();
        System.out.println("Opening git folder");
        try (Git git = GitUtil.open(codeFolder)) {
            System.out.println("Getting branch names");
            Set<String> branchNames = GitUtil.getBranchNames(git);
            String cleanBranch = "repo-cleaner-" + "master";// TODO GitUtil.getUnusedBranchName(git);
            System.out.println("Checking out new branch");
            GitUtil.checkoutNewBranch(git, cleanBranch);
        }
        System.out.println("Getting language graphs");
        Map<Language, Graph> languageGraphs = getLanguageGraphs(codeFolder);
        System.out.println("Cleaning");
        CleanResult cleanResult = clean(languageGraphs, initiator, config);
        System.out.println("Checking valid clean");
        checkValidClean(cleanResult, initiator);
        System.out.println("Committing");
        commit(codeFolder, config);
        System.out.println("cleaned");
        cleanIO.cleaned(cleanResult);
    }

    private static Map<Language, Graph> getLanguageGraphs(File sourceFolder) throws RepoCleanerException {
        System.out.println("Getting language graphs");
        Path sourceFolderPath = Paths.get(sourceFolder.getAbsolutePath());
        try {
            List<String> fileNames = Files.walk(sourceFolderPath)
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .map(File::getAbsolutePath)
                    .collect(Collectors.toList());
            System.out.println("Adding files to graph builder");
            GraphsBuilder graphsBuilder = new GraphsBuilder();
            for (String fileName : fileNames) {
                graphsBuilder.addFile(fileName);
            }
            System.out.println("Building graphs");
            return graphsBuilder.build();
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to clean repository", e);
        }
    }

    private static CleanResult clean(Map<Language, Graph> languageGraphs, Initiator initiator, Config config) throws RepoCleanerException {
        TransformationCoster coster = new PlainCoster();
        Transformer transformer = new EOFTransformer("// EOF\n");
        int totalCost = 0;
        StringBuilder description = new StringBuilder();
        long maxCredits = initiator.getMaxCredits();
        maxCredits = 10000;
        GraphWriter graphWriter = new GraphWriter();
        System.out.println("transforming graphs");
        for (Graph graph : languageGraphs.values()) {
            Transformation transformation = transformer.createTransformation(graph);
            System.out.println("Transformation: " + transformation);
            if (transformation != null) {
                int cost = coster.calculateTokenCost(transformation);
                System.out.println("Cost: " + cost);
                int newTotalCost = totalCost + cost;
                System.out.println("Total cost: " + totalCost);
                System.out.println("maxCredits: " + maxCredits);
                if (newTotalCost > maxCredits) {
                    System.out.println("total cost is more than max credits");
                    break;
                }
                totalCost = newTotalCost;
                description.append(transformation.getDescription());
                description.append('\n');
                System.out.println("Executing transformation commands");
                transformation.getCommands().forEach(command -> command.execute(graph));
                graphWriter.write(graph);
            }
        }
        System.out.println("Finished transforming graphs");
        return new CleanResult(totalCost, description.toString());
    }

    private static void checkValidClean(CleanResult cleanResult, Initiator initiator) {
        int creditCost = cleanResult.getCreditCost();
        if (creditCost == 0) {
            System.out.println("No cleaning done");
        }
        if (initiator.getMaxCredits() < creditCost) {
            System.out.println("Not enough credits to cover cleaning");
        }
    }

    private static void commit(File sourceFolder, Config config) throws RepoCleanerException {
        System.out.println("Committing changes");
        try (Git git = GitUtil.open(sourceFolder)) {
            System.out.println("git add all");
            GitUtil.addAll(git);
            System.out.println("Getting the zone id from config");
            ZoneId zoneId;
            try {
                System.out.println("ZoneID: " + config.getZoneId());
                zoneId = ZoneId.of(config.getZoneId());
            } catch (NullPointerException e) {
                System.out.println("Failed to get zone id from " + config.getZoneId() + ", using UTC");
                zoneId = ZoneId.of("UTC");
            }
            System.out.println(zoneId);
            System.out.println("Creating local date time from zone id " + zoneId);
            LocalDateTime now = LocalDateTime.now(zoneId);
            System.out.println("committing changes");
            GitUtil.commit(git, "Cleaned by www.repocleaner.com at " + now);
        }
    }
}
