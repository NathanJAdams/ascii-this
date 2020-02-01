package com.twitterbot.data.retrieve;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsertUrlCreator implements UrlCreator {
    private final String urlPrefix;
    private final String urlSuffix;

    public InsertUrlCreator(String urlPrefix) {
        this(urlPrefix, null);
    }

    @Override
    public String createUrl(String account) {
        StringBuilder sb = new StringBuilder();
        if (urlPrefix != null) {
            sb.append(urlPrefix);
        }
        sb.append(account);
        if (urlSuffix != null) {
            sb.append(urlSuffix);
        }
        return sb.toString();
    }
}
