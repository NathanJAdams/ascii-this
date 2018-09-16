package com.repocleaner.initiator;

import com.google.gson.GsonBuilder;
import com.repocleaner.model.initiator.Initiator;
import com.repocleaner.util.json.GsonCustomiser;
import com.repocleaner.util.json.RuntimeTypeAdapterFactory;

public class InitiatorGsonCustomiser implements GsonCustomiser {
    @Override
    public void customise(GsonBuilder builder) {
        RuntimeTypeAdapterFactory<Initiator> factory = new RuntimeTypeAdapterFactory<>(Initiator.class);
        factory.registerSubtype(ApiInitiator.class, "api");
        factory.registerSubtype(CronInitiator.class, "cron");
        factory.registerSubtype(WebsiteInitiator.class, "web");
        builder.registerTypeAdapter(Initiator.class, factory);
    }
}