package com.twitterbot.data.retrieve;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlainUrlCreator implements UrlCreator {
    private final String url;

    @Override
    public String createUrl(String account) {
        return url;
    }
}
