package com.repocleaner.lambdaprepare;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.repocleaner.initiator.ApiInitiator;
import com.repocleaner.initiator.InitiatorGsonCustomiser;
import com.repocleaner.io.external.PrepareIO;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.receive.LifecycleRequest;
import com.repocleaner.model.source.Source;
import com.repocleaner.model.user.Config;
import com.repocleaner.model.user.SplitType;
import com.repocleaner.prepare.Preparer;
import com.repocleaner.s3.S3PrepareIO;
import com.repocleaner.source.RepoHostSource;
import com.repocleaner.source.SourceGsonCustomiser;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.json.JsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PrepareLambda implements RequestHandler<S3Event, Void> {
    public static void main(String[] args) {
        String id = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        Initiator initiator = new ApiInitiator(1000, "/endpoint");
        Map<String, String> languageVersions = new HashMap<>();
        Map<String, Boolean> splitTypes = new HashMap<>();
        languageVersions.put("Java", "8");
        languageVersions.put("Python", "3.7.0");
        splitTypes.put(SplitType.Effect.name(), true);
        Config config = new Config(languageVersions, splitTypes);
        Source source = new RepoHostSource("github", "NathanJAdams", "verJ", "master", "token");
        LifecycleRequest lifecycleRequest = new LifecycleRequest(id, token, initiator, config, source);
        JsonUtil jsonUtil = new JsonUtil(new SourceGsonCustomiser(), new InitiatorGsonCustomiser());
        System.out.println(jsonUtil.toJson(lifecycleRequest));
    }

    public Void handleRequest(S3Event event, Context context) {
        System.out.println(new JsonUtil().toJson(event));
        try {
            PrepareIO prepareIO = new S3PrepareIO(event);
            Preparer.prepare(prepareIO);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
