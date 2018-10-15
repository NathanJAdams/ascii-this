package com.repocleaner.lifecycletest;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.repocleaner.corescheduleapi.ApiScheduler;
import com.repocleaner.io.external.UserIO;
import com.repocleaner.json.customisers.InitiatorGsonCustomiser;
import com.repocleaner.json.customisers.SourceGsonCustomiser;
import com.repocleaner.json.customisers.TransformationInfoGsonCustomiser;
import com.repocleaner.model.CleanResult;
import com.repocleaner.model.Config;
import com.repocleaner.model.FileStructure;
import com.repocleaner.model.LanguageConfig;
import com.repocleaner.model.LifecycleRequest;
import com.repocleaner.model.Sink;
import com.repocleaner.model.Source;
import com.repocleaner.model.TransformationMetaData;
import com.repocleaner.model.UsageToken;
import com.repocleaner.model.User;
import com.repocleaner.model.transform.SplitType;
import com.repocleaner.source.ZipFileSource;
import com.repocleaner.transformation.transformations.EOFTransformationMetaData;
import com.repocleaner.util.IOUtils;
import com.repocleaner.util.LocalDateTimeUtil;
import com.repocleaner.util.RepoCleanerException;
import com.repocleaner.util.ZipUtil;
import com.repocleaner.util.json.GsonCustomiser;
import com.repocleaner.util.json.JsonUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LifecycleTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testLifecycleApi() throws RepoCleanerException, IOException {
        Config config = mockConfig();
        UsageToken usageToken = mockUsageToken(config);
        User user = mockUser(usageToken);
        UserIO userIO = mockUserIO(user);
        String userId = "user-id";
        String token = "token";
        Source source = mockSource();
        Sink sink = mockSink();
        FileStructure fileStructure = mockFileStructure(sink);
        LifecycleSkeleton skeleton = new LifecycleSkeleton(fileStructure, this::onScheduled, this::onPrepared, this::onCleaned, this::onSent);
        ApiScheduler.schedule(userIO, skeleton, userId, token, source, sink);
    }

    private Config mockConfig() {
        Config config = new Config();
        Map<String, LanguageConfig> languageConfigs = new HashMap<>();
        LanguageConfig languageConfig = mockJavaLanguageConfig();
        languageConfigs.put("Java", languageConfig);
        config.setLanguageConfigs(languageConfigs);
        return config;
    }

    private LanguageConfig mockJavaLanguageConfig() {
        LanguageConfig languageConfig = new LanguageConfig();
        languageConfig.setVersion("8");
        Map<String, Boolean> splitTypes = new HashMap<>();
        splitTypes.put(SplitType.Transformation.name(), true);
        languageConfig.setSplitTypes(splitTypes);
        Map<String, TransformationMetaData> transformationInfos = new HashMap<>();
        transformationInfos.put("a", new EOFTransformationMetaData(10, "// EOF"));
        languageConfig.setTransformationInfos(transformationInfos);
        return languageConfig;
    }

    private UsageToken mockUsageToken(Config config) {
        UsageToken usageToken = new UsageToken();
        LocalDateTime time = LocalDateTime.now().plusMinutes(1);
        String expiryTimeHex = LocalDateTimeUtil.toHex(time);
        usageToken.setExpiryTimeHex(expiryTimeHex);
        usageToken.setNotificationEndPoint("end-point");
        usageToken.setConfig(config);
        return usageToken;
    }

    private User mockUser(UsageToken usageToken) {
        User user = new User();
        Map<String, UsageToken> usageTokens = new HashMap<>();
        usageTokens.put("token", usageToken);
        user.setUsageTokens(usageTokens);
        user.setCredits(100);
        user.setMaxCreditsPerClean(10);
        return user;
    }

    private Source mockSource() throws RepoCleanerException, IOException {
        File codeFolder = temporaryFolder.newFolder();
        System.out.println(codeFolder.exists());
        File sourceFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "../../src/main/test/com/repocleaner/lifecycletest/LifecycleSkeleton.java");
        File testFile = new File(codeFolder, "Hello.java");
        testFile.createNewFile();
        File zippedFile = new File(temporaryFolder.getRoot(), "zipped.zip");
        IOUtils.copy(sourceFile, testFile);
        ZipUtil.zip(codeFolder, zippedFile);
        return new ZipFileSource(zippedFile.getAbsolutePath());
    }

    private Sink mockSink() throws RepoCleanerException {
        Sink sink = mock(Sink.class);
        doAnswer(invocationOnMock -> {
            System.out.println("Sinking");
            File codeFolder = invocationOnMock.getArgument(0);
            CleanResult cleanResult = invocationOnMock.getArgument(1);
            System.out.println("Code Folder " + codeFolder);
            System.out.println("Clean Result: " + cleanResult);
            return null;
        }).when(sink).upload(any(), any(), any());
        return sink;
    }

    private UserIO mockUserIO(User user) throws RepoCleanerException {
        UserIO userIO = mock(UserIO.class);
        when(userIO.getUser("user-id")).thenReturn(user);
        return userIO;
    }

    private FileStructure mockFileStructure(Sink mockSink) throws IOException {
        File rootParentFolder = temporaryFolder.newFolder();
        String key = UUID.randomUUID().toString();
        GsonCustomiser mockedSinkGsonCustomiser = builder -> {
            builder.registerTypeAdapter(Sink.class, new TypeAdapter<Sink>() {
                @Override
                public void write(JsonWriter out, Sink value) throws IOException {
                    out.beginObject();
                    out.endObject();
                }

                @Override
                public Sink read(JsonReader in) throws IOException {
                    in.beginObject();
                    in.endObject();
                    return mockSink;
                }
            });
        };
        JsonUtil jsonUtil = new JsonUtil(new InitiatorGsonCustomiser(), new SourceGsonCustomiser(), mockedSinkGsonCustomiser, new TransformationInfoGsonCustomiser());
        return new FileStructure(rootParentFolder, key, jsonUtil);
    }

    private void onScheduled(LifecycleRequest lifecycleRequest) {
        System.out.println("scheduled");
        System.out.println(lifecycleRequest.getConfig());
        System.out.println(lifecycleRequest.getInitiator());
        System.out.println(lifecycleRequest.getSource());
        System.out.println(lifecycleRequest.getSink());
    }

    private void onPrepared() {
        System.out.println("prepared");
    }

    private void onCleaned(CleanResult cleanResult) {
        System.out.println("cleaned");
        System.out.println(cleanResult);
    }

    private void onSent() {
        System.out.println("sent");
    }
}
