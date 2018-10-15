package com.repocleaner.json.customisers;

import com.google.gson.GsonBuilder;
import com.repocleaner.model.Sink;
import com.repocleaner.sink.RepoHostSink;
import com.repocleaner.sink.ZipFileSink;
import com.repocleaner.util.json.GsonCustomiser;
import com.repocleaner.util.json.RuntimeTypeAdapterFactory;

public class SinkGsonCustomiser implements GsonCustomiser {
    @Override
    public void customise(GsonBuilder builder) {
        RuntimeTypeAdapterFactory<Sink> factory = new RuntimeTypeAdapterFactory<>(Sink.class);
        factory.registerSubtype(RepoHostSink.class, "repo-host");
        factory.registerSubtype(ZipFileSink.class, "zip-file");
        builder.registerTypeAdapterFactory(factory);
    }
}
