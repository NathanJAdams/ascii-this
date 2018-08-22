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
import com.repocleaner.config.Config;
import com.repocleaner.initiator.Initiator;
import com.repocleaner.s3.S3FileDeleter;
import com.repocleaner.s3.S3FileDownloader;
import com.repocleaner.s3.S3FileUploader;
import com.repocleaner.s3.S3Info;
import com.repocleaner.util.FileStructure;
import com.repocleaner.util.GitUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.TokenCost;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.JsonUtil;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Cleaner {
    private static final JsonUtil JSON_UTIL = new JsonUtil();

    public static void clean(String bucket, String key) throws RepoCleanerException {
        try (FileStructure fileStructure = new FileStructure(key)) {
            File rootFolder = fileStructure.getRootFolder();
            File codeFolder = fileStructure.getCodeFolder();
            File initiatorFile = fileStructure.getInitiatorFile();
            File configFile = fileStructure.getConfigFile();
            File tokenCostFile = fileStructure.getTokenCostFile();
            File zippedFile = fileStructure.getZippedFile();

            S3FileDownloader.download(bucket, key, zippedFile);
            ZipUtil.extract(zippedFile, rootFolder);
            Config config = JSON_UTIL.fromJsonFileOrNull(configFile, Config.class);
            Initiator initiator = JSON_UTIL.fromJsonFileOrNull(initiatorFile, Initiator.class);

            Git git = GitUtil.init(codeFolder);
            Set<String> branchNames = GitUtil.getBranchNames(git);
            String cleanBranch = "repo-cleaner-" + "master";// TODO GitUtil.getUnusedBranchName(git);
            GitUtil.checkoutNewBranch(git, cleanBranch);
            TokenCost tokenCost = clean(codeFolder, initiator, config);
            postCheck(tokenCost, initiator);

            configFile.delete();
            zippedFile.delete();
            JSON_UTIL.toJsonFile(tokenCost, tokenCostFile);
            ZipUtil.zip(rootFolder, zippedFile);
            S3FileUploader.upload(S3Info.CLEANED_BUCKET, key, zippedFile);
        } finally {
            S3FileDeleter.delete(bucket, key);
        }
    }

    private static void postCheck(TokenCost tokenCost, Initiator initiator) throws RepoCleanerException {
        int cost = tokenCost.getTokenCost();
        if (cost == 0) {
            throw new RepoCleanerException("No changes made");
        }
        if (initiator.getTokens() < cost) {
            throw new RepoCleanerException("Insufficient tokens");
        }
    }

    private static TokenCost clean(File sourceFolder, Initiator initiator, Config config) throws RepoCleanerException {
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
        int maxTokens = config.getMaxTokensPerClean();
        GraphWriter graphWriter = new GraphWriter();
        for (Graph graph : graphs.values()) {
            Transformation transformation = transformer.createTransformation(graph);
            int cost = coster.calculateTokenCost(transformation);
            int newTotalCost = totalCost + cost;
            if (newTotalCost > maxTokens) {
                break;
            }
            totalCost = newTotalCost;
            transformation.getCommands().forEach(command -> command.execute(graph));
            graphWriter.write(graph);
        }
        return new TokenCost(totalCost);
    }
}
