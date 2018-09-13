package com.repocleaner.apicron;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.repocleaner.firebase.DatabaseReferenceCreator;
import com.repocleaner.firebase.DbGetter;
import com.repocleaner.firebase.QueryGetter;
import com.repocleaner.initiator.CronInitiator;
import com.repocleaner.initiator.InitiatorGsonCustomiser;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.model.source.Source;
import com.repocleaner.model.user.Config;
import com.repocleaner.model.user.HostedAccount;
import com.repocleaner.model.user.HostedRepo;
import com.repocleaner.model.user.User;
import com.repocleaner.s3.S3FileUploader;
import com.repocleaner.s3.S3Info;
import com.repocleaner.source.RepoHostSource;
import com.repocleaner.source.SourceGsonCustomiser;
import com.repocleaner.util.FileStructure;
import com.repocleaner.util.LocalDateTimeUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.JsonUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class CronApi {
    private static final JsonUtil JSON_UTIL = new JsonUtil(new InitiatorGsonCustomiser(), new SourceGsonCustomiser());

    public static void main(String[] args) {
        CronApi.startCleanJobs();
    }

    public static void startCleanJobs() {
        String maxTimeHex = LocalDateTimeUtil.toHex(LocalDateTime.now());
        DatabaseReference reposRef = DatabaseReferenceCreator.DB_CONNECTION
                .child("repos");
        Query query = reposRef.orderByChild("nextCleanTimeHex")
                .endAt(maxTimeHex);
        Map<String, HostedRepo> hostedRepos = new QueryGetter<>(query, HostedRepo.class).get();
        hostedRepos.values().forEach(CronApi::startCleanJob);
    }

    private static void startCleanJob(HostedRepo hostedRepo) {
        String userId = hostedRepo.getUserId();
        User user = getUser(userId);
        if (!isValidUser(user)) {
            return;
        }
        String accountId = hostedRepo.getAccountId();
        HostedAccount hostedAccount = user.getAccounts().get(accountId);
        Initiator initiator = createInitiator(user);
        Source source = createSource(hostedAccount, hostedRepo);
        Config config = hostedRepo.getConfig();

        String id = UUID.randomUUID().toString();
        FileStructure fileStructure = new FileStructure(id);
        try {
            JSON_UTIL.toJsonFile(initiator, fileStructure.getInitiatorFile());
            JSON_UTIL.toJsonFile(source, fileStructure.getSourceFile());
            JSON_UTIL.toJsonFile(config, fileStructure.getConfigFile());
            File zippedFile = fileStructure.getZippedFile();
            ZipUtil.zip(fileStructure.getRootFolder(), zippedFile);
            S3FileUploader.upload(S3Info.WAITING_BUCKET, id, zippedFile);
        } catch (RepoCleanerException e) {
            e.printStackTrace();
        }
    }

    private static User getUser(String userId) {
        System.out.println("Get user: " + userId);
        DatabaseReference userRef = DatabaseReferenceCreator.DB_CONNECTION
                .child("users")
                .child(userId);
        return new DbGetter<>(userRef, User.class).get();
    }

    private static boolean isValidUser(User user) {
        System.out.println("is valid user: " + user);
        if (!user.isActive()) {
            System.out.println("User is not active");
            return false;
        }
        if (user.getCredits() <= 0) {
            System.out.println("User has no credits");
            return false;
        }
        return true;
    }

    private static Initiator createInitiator(User user) {
        long credits = user.getCredits();
        long maxCreditsPerClean = user.getMaxCreditsPerClean();
        long maxCredits = Math.min(credits, maxCreditsPerClean);
        return new CronInitiator(maxCredits);
    }

    private static Source createSource(HostedAccount hostedAccount, HostedRepo hostedRepo) {
        String host = hostedAccount.getHost();
        String userName = hostedAccount.getUserName();
        String repoName = hostedRepo.getRepoName();
        String masterBranch = hostedRepo.getMasterBranch();
        String personalAccessToken = hostedAccount.getPersonalAccessToken();
        return new RepoHostSource(host, userName, repoName, masterBranch, personalAccessToken);
    }
}
