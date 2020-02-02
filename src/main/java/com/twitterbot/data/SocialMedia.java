package com.twitterbot.data;

import com.twitterbot.data.retrieve.InsertUrlCreator;
import com.twitterbot.data.retrieve.ParseCountExtractor;
import com.twitterbot.data.retrieve.TwitterApiCountRetriever;
import com.twitterbot.data.retrieve.UrlCountRetriever;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialMedia {
    Twitter("Twitter followers", new TwitterApiCountRetriever()),
    Facebook("Facebook likes", new UrlCountRetriever(
            new InsertUrlCreator("https://facebook.com/"),
            null,
            new ParseCountExtractor("([0-9,]+) *< *span *class *= *\"_50f8 _50f4 _5kx5\" *> *likes"))),
    Instagram("Instagram followers", new UrlCountRetriever(
            new InsertUrlCreator("https://instagram.com/"),
            null,
            new ParseCountExtractor("\"edge_followed_by\" *: *\\{ *\"count\" *: *(\\d+) *}"))),
    YouTube("YouTube views", new UrlCountRetriever(
            new InsertUrlCreator("https://youtube.com/", "/about"),
            null,
            new ParseCountExtractor("<span *class *= *\"about-stat\" *>.*< *b *> *([0-9,]+) *< */b *> *views *< */span *>")));

    @Getter
    private final String accountDescription;
    private final CountRetriever countRetriever;

    public int getLatestCount(String account) {
        if (account == null || account.isEmpty()) {
            return -1;
        }
        System.out.println("Getting " + getAccountDescription() + " for " + account);
        return countRetriever.retrieveCount(account);
    }
}
