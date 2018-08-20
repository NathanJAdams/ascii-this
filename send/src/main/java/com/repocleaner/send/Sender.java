package com.repocleaner.send;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.repocleaner.sinkinfo.RepoHostSink;
import com.repocleaner.sinkinfo.Sink;
import com.repocleaner.sinkinfo.WebSink;
import com.repocleaner.userinfo.UserInfo;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.RuntimeTypeAdapterFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Sender {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapterFactory(
                    new RuntimeTypeAdapterFactory<>(Sink.class)
                            .registerSubtype(RepoHostSink.class, "repo-host")
                            .registerSubtype(WebSink.class, "web")
            )
            .create();

    public static UserInfo send(File folder) throws RepoCleanerException {
        Sink sink = getSink(folder);
        File sourceFolder = new File(folder, "source");
        File tempFile = createTempFile();
        sink.sendSource(sourceFolder, tempFile);
        return getUserInfo(folder);
    }

    private static Sink getSink(File folder) throws RepoCleanerException {
        File sinkDetailsFile = new File(folder, "sink.json");
        if (!sinkDetailsFile.exists()) {
            throw new RepoCleanerException("No sink details present");
        }
        try (FileReader fr = new FileReader(sinkDetailsFile)) {
            return GSON.fromJson(fr, Sink.class);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to create sink from json", e);
        }
    }

    private static UserInfo getUserInfo(File folder) throws RepoCleanerException {
        File userInfoFile = new File(folder, "user-info.json");
        if (!userInfoFile.exists()) {
            throw new RepoCleanerException("No user info details present");
        }
        try (FileReader fr = new FileReader(userInfoFile)) {
            return GSON.fromJson(fr, UserInfo.class);
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to create user info from json", e);
        }
    }

    private static File createTempFile() throws RepoCleanerException {
        try {
            return File.createTempFile("temp", "");
        } catch (IOException e) {
            throw new RepoCleanerException("Failed to save cleaned repo to file", e);
        }
    }
}
