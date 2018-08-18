package clean.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.eclipse.jgit.api.Git;
import clean.graph.Graph;
import clean.language.GraphWriter;
import clean.language.GraphsBuilder;
import clean.language.Language;
import clean.source.Source;
import clean.transform.Transformation;
import clean.transform.TransformationCoster;
import clean.transform.Transformer;
import clean.transform.coster.PlainCoster;
import clean.transform.transformers.EOFTransformer;
import clean.user.User;
import clean.util.GitUtil;
import clean.util.GsonUtil;
import clean.util.RepoCleanerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AwsCleanRepoLambda implements RequestHandler<String, LambdaResponse> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LambdaResponse handleRequest(String requestJson, Context context) {
        LambdaRequest request = GsonUtil.fromJsonOrNull(requestJson, LambdaRequest.class);
        if (request == null) {
            return LambdaResponse.BAD_INPUT_RESPONSE;
        }
        User user = request.getUser();
        Source source = request.getSource();

        String requestId = context.getAwsRequestId();
        if (requestId == null) {
            return LambdaResponse.SERVER_ERROR_RESPONSE;
        }
        // TODO replace with /tmp/ + requestId
        File sourceFolder = new File("C:\\Users\\Nathan\\" + requestId);
        try {
            if (sourceFolder.mkdir()) {
                process(sourceFolder, user, source);
                sourceFolder.delete();
            }
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return new LambdaResponse(false, e.getMessage());
        }
        return LambdaResponse.SUCCESS_RESPONSE;
    }

    private void process(File sourceFolder, User user, Source source) throws RepoCleanerException {
        if (user.countTokens() <= 0) {
            throw new RepoCleanerException("No tokens remaining");
        }
        if (!user.canClean()) {
            throw new RepoCleanerException("User rejected the repo clean");
        }
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

    private int clean(File sourceFolder, User user) throws RepoCleanerException {
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
            throw new RepoCleanerException("Failed to clean repository", e);
        }
        Map<Language, Graph> graphs = graphsBuilder.build();
        TransformationCoster coster = new PlainCoster();
        Transformer transformer = new EOFTransformer("// EOF");
        int totalCost = 0;
        int userTokens = user.countTokens();
        GraphWriter graphWriter = new GraphWriter();
        for (Graph graph : graphs.values()) {
            Transformation transformation = transformer.createTransformation(graph);
            int cost = coster.calculateTokenCost(transformation);
            int newTotalCost = totalCost + cost;
            if (newTotalCost > userTokens) {
                return totalCost;
            }
            totalCost = newTotalCost;
            transformation.getCommands().forEach(command -> command.execute(graph));
            graphWriter.write(graph);
        }
        return totalCost;
    }
}
