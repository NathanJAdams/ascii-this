package com.repocleaner.apicron;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClient;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbGetter;
import com.repocleaner.firebase.QueryGetter;
import com.repocleaner.initiator.CronInitiator;
import com.repocleaner.io.PrepareLambdaRequest;
import com.repocleaner.model.config.Config;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.source.Source;
import com.repocleaner.model.user.HostedKey;
import com.repocleaner.model.user.HostedRepo;
import com.repocleaner.model.user.User;
import com.repocleaner.s3.S3Info;
import com.repocleaner.source.RepoHostSource;
import com.repocleaner.util.LocalDateTimeUtil;
import com.repocleaner.util.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;

public class CronApi {
    public static void startCleanJobs() {
        String maxTimeHex = LocalDateTimeUtil.toHex(LocalDateTime.now());
        Query query = DatabaseReferenceCreator.DB_CONNECTION
                .child("hostedRepos")
                .orderByChild("nextCleanDateTimeHex")
                .endAt(maxTimeHex)
                .limitToFirst(1000);
        List<HostedRepo> hostedRepos = new QueryGetter<>(query, HostedRepo.class).get();
        hostedRepos.forEach(CronApi::startCleanJob);
    }

    private static void startCleanJob(HostedRepo hostedRepo) {
        HostedKey hostedKey = hostedRepo.getHostedKey();
        User user = getUser(hostedKey.getUserEmail());
        long credits = user.getCredits();
        if (credits <= 0) {
            return;
        }
        Initiator initiator = new CronInitiator(credits);
        Config config = user.getConfig();
        String host = hostedKey.getHost();
        String userName = hostedKey.getUserName();
        String repo = hostedRepo.getRepo();
        String masterBranch = hostedRepo.getMasterBranch();
        String token = null; // TODO hostedKey.getUserName();
        Source source = new RepoHostSource(host, userName, repo, masterBranch, token);
        PrepareLambdaRequest prepareLambdaRequest = new PrepareLambdaRequest(initiator, config, source);
        String payload = new JsonUtil().toJson(prepareLambdaRequest);
        AWSLambdaAsync async = AWSLambdaAsyncClient.asyncBuilder()
                .withRegion(Regions.EU_WEST_1)
                .build();
        InvokeRequest invokeRequest = new InvokeRequest();
        invokeRequest.withFunctionName(S3Info.LAMBDA_PREPARE_NAME)
                .withInvocationType(InvocationType.Event)
                .withPayload(payload);
        async.invoke(invokeRequest);
    }

    private static User getUser(String userEmail) {
        DatabaseReference creditsDbRef = DatabaseReferenceCreator.DB_CONNECTION
                .child("users")
                .child(userEmail);
        return new DbGetter<>(creditsDbRef, User.class).get();
    }
}
