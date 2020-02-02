package com.twitterbot.data.retrieve;

import com.twitterbot.TwitterAPI;
import com.twitterbot.data.CountRetriever;

public class TwitterApiCountRetriever implements CountRetriever {
    @Override
    public int retrieveCount(String account) {
        return TwitterAPI.getFollowerCount(account);
    }
}
