package com.repocleaner.source;

import com.google.gson.GsonBuilder;
import com.repocleaner.model.source.Source;
import com.repocleaner.util.json.GsonCustomiser;
import com.repocleaner.util.json.RuntimeTypeAdapterFactory;

public class SourceGsonCustomiser implements GsonCustomiser {
    @Override
    public void customise(GsonBuilder builder) {
        RuntimeTypeAdapterFactory<Source> factory = new RuntimeTypeAdapterFactory<>(Source.class);
        factory.registerSubtype(RepoHostSource.class, "repo-host");
        factory.registerSubtype(ZipFileSource.class, "zip-file");
        builder.registerTypeAdapter(Source.class, factory);
    }
}
