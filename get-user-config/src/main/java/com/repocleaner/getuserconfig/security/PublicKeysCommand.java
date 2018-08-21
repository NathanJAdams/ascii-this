package com.repocleaner.getuserconfig.security;

import com.google.gson.Gson;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.repocleaner.util.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class PublicKeysCommand extends HystrixCommand<PublicKeys> {
    private static final String GROUP = "PUBLIC_KEYS";

    private final String publicKeysUrl;

    public PublicKeysCommand(String publicKeysUrl) {
        super(HystrixCommandGroupKey.Factory.asKey(GROUP));
        this.publicKeysUrl = publicKeysUrl;
    }

    @Override
    protected PublicKeys run() throws Exception {
        URL url = new URL(publicKeysUrl);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        String response = IOUtils.toString(is, StandardCharsets.UTF_8);
        return new Gson().fromJson(response, PublicKeys.class);
    }

    @Override
    protected PublicKeys getFallback() {
        return null;
    }
}
