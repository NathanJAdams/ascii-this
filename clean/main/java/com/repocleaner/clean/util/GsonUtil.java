package com.repocleaner.clean.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.repocleaner.clean.graph.match.GraphElementMatcher;
import com.repocleaner.clean.graph.match.matchers.edge.AndEdgeMatcher;
import com.repocleaner.clean.graph.match.matchers.edge.AnyEdgeMatcher;
import com.repocleaner.clean.graph.match.matchers.edge.OrEdgeMatcher;
import com.repocleaner.clean.graph.match.matchers.edge.PropertyEqualsEdgeMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.AndVertexMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.AnyVertexMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.OrVertexMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.PropertyEqualsVertexMatcher;
import com.repocleaner.clean.graph.match.matchers.vertex.SourceTextRegexVertexMatcher;
import com.repocleaner.clean.source.RepoHostSource;
import com.repocleaner.clean.source.Source;
import com.repocleaner.clean.source.ZipSource;
import com.repocleaner.clean.user.ApiUser;
import com.repocleaner.clean.user.ConfigUser;
import com.repocleaner.clean.user.User;
import com.repocleaner.clean.user.WebsiteUser;
import com.repocleaner.clean.user.config.schedule.ManualScheduleType;
import com.repocleaner.clean.user.config.schedule.ScheduleType;
import com.repocleaner.clean.user.config.schedule.TimeScheduleType;
import com.repocleaner.clean.user.config.schedule.WebHookScheduleType;
import com.repocleaner.clean.user.config.split.EffectSplitType;
import com.repocleaner.clean.user.config.split.FileSplitType;
import com.repocleaner.clean.user.config.split.LinesOfCodeCostSplitType;
import com.repocleaner.clean.user.config.split.PopularitySplitType;
import com.repocleaner.clean.user.config.split.RiskSplitType;
import com.repocleaner.clean.user.config.split.SplitType;
import com.repocleaner.clean.user.config.split.TokenCostSplitType;
import com.repocleaner.clean.user.config.split.TransformationSplitType;

import java.time.LocalDateTime;

public class GsonUtil {
    private static final Gson GSON;

    static {
        RuntimeTypeAdapterFactory<GraphElementMatcher> matcherFactory = new RuntimeTypeAdapterFactory<>(GraphElementMatcher.class)
                .registerSubtype(AndEdgeMatcher.class, "e-and")
                .registerSubtype(AnyEdgeMatcher.class, "e-any")
                .registerSubtype(OrEdgeMatcher.class, "e-or")
                .registerSubtype(PropertyEqualsEdgeMatcher.class, "e-prop")
                .registerSubtype(AndVertexMatcher.class, "v-and")
                .registerSubtype(AnyVertexMatcher.class, "v-any")
                .registerSubtype(OrVertexMatcher.class, "v-or")
                .registerSubtype(PropertyEqualsVertexMatcher.class, "v-prop")
                .registerSubtype(SourceTextRegexVertexMatcher.class, "v-regex");
        RuntimeTypeAdapterFactory<Source> sourceFactory = new RuntimeTypeAdapterFactory<>(Source.class)
                .registerSubtype(RepoHostSource.class, "host")
                .registerSubtype(ZipSource.class, "jar");
        RuntimeTypeAdapterFactory<User> userFactory = new RuntimeTypeAdapterFactory<>(User.class)
                .registerSubtype(ApiUser.class, "api")
                .registerSubtype(ConfigUser.class, "config")
                .registerSubtype(WebsiteUser.class, "website");
        RuntimeTypeAdapterFactory<ScheduleType> scheduleFactory = new RuntimeTypeAdapterFactory<>(ScheduleType.class)
                .registerSubtype(ManualScheduleType.class, "manual")
                .registerSubtype(TimeScheduleType.class, "time")
                .registerSubtype(WebHookScheduleType.class, "webhook");
        RuntimeTypeAdapterFactory<SplitType> splitFactory = new RuntimeTypeAdapterFactory<>(SplitType.class)
                .registerSubtype(EffectSplitType.class, "effect")
                .registerSubtype(FileSplitType.class, "file")
                .registerSubtype(LinesOfCodeCostSplitType.class, "lines-of-code")
                .registerSubtype(PopularitySplitType.class, "popularity")
                .registerSubtype(RiskSplitType.class, "risk")
                .registerSubtype(TokenCostSplitType.class, "token-cost")
                .registerSubtype(TransformationSplitType.class, "transformation");

        GSON = new GsonBuilder()
                .registerTypeAdapterFactory(matcherFactory)
                .registerTypeAdapterFactory(sourceFactory)
                .registerTypeAdapterFactory(userFactory)
                .registerTypeAdapterFactory(scheduleFactory)
                .registerTypeAdapterFactory(splitFactory)
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeSerializer())
                .setPrettyPrinting()
                .create();
    }

    public static <T> T fromJsonOrNull(String json, Class<T> tClass) {
        try {
            return GSON.fromJson(json, tClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static String toJson(Object object) {
        try {
            return GSON.toJson(object);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }
}
