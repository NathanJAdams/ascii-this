package com.repocleaner.lambdarequestweb;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.repocleaner.core.initiators.WebInitiator;
import com.repocleaner.core.io.RequestIO;
import com.repocleaner.core.model.CleanRequest;
import com.repocleaner.core.model.Config;
import com.repocleaner.core.model.Initiator;
import com.repocleaner.core.model.LanguageConfig;
import com.repocleaner.core.model.Sink;
import com.repocleaner.core.model.Source;
import com.repocleaner.core.model.TransformationMetaData;
import com.repocleaner.core.util.RepoCleanerException;
import com.repocleaner.core.util.json.JsonUtil;
import com.repocleaner.core.util.rest.ResponseUtil;
import com.repocleaner.s3.S3RequestIO;
import com.repocleaner.sink.ZipFileSink;
import com.repocleaner.sink.ZipResponseType;
import com.repocleaner.source.RepoHostSource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestWebLambda implements RequestHandler<RequestWebRequest, RequestWebResponse> {
    private static final RequestIO SCHEDULE_IO = new S3RequestIO(new JsonUtil());
    private static final Config CONFIG;
    private static final Initiator INITIATOR = new WebInitiator();

    static {
        Map<String, LanguageConfig> languageConfigs = new HashMap<>();
        String version = "1.8";
        Map<String, Boolean> splitTypes = new HashMap<>();
        Map<String, TransformationMetaData> transformationInfos = new HashMap<>();
        LanguageConfig javaLanguageConfig = new LanguageConfig(version, splitTypes, transformationInfos);
        languageConfigs.put("java", javaLanguageConfig);
        String zoneId = "UTC";
        CONFIG = new Config(languageConfigs, zoneId);
    }

    @Override
    public RequestWebResponse handleRequest(RequestWebRequest request, Context context) {
        request.preCheck();
        String provider = request.getProvider();
        String user = request.getUser();
        String repo = request.getRepo();
        String branch = request.getBranch();

        String id = UUID.randomUUID().toString();
        Source source = new RepoHostSource(provider, user, repo, branch);
        Sink sink = new ZipFileSink(id, ZipResponseType.Diff);
        CleanRequest cleanRequest = new CleanRequest(id, INITIATOR, CONFIG, source, sink);
        try {
            SCHEDULE_IO.request(cleanRequest);
            return new RequestWebResponse(id);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
            return ResponseUtil.internalError("Schedule clean");
        }
    }
}
