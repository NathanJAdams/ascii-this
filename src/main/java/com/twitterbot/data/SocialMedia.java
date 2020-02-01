package com.twitterbot.data;

import com.twitterbot.data.retrieve.DoOutputConnectionModifier;
import com.twitterbot.data.retrieve.FollowersBody;
import com.twitterbot.data.retrieve.InsertUrlCreator;
import com.twitterbot.data.retrieve.JsonContentConnectionModifier;
import com.twitterbot.data.retrieve.JsonCountExtractor;
import com.twitterbot.data.retrieve.ListConnectionModifier;
import com.twitterbot.data.retrieve.POSTConnectionModifier;
import com.twitterbot.data.retrieve.ParseCountExtractor;
import com.twitterbot.data.retrieve.PlainUrlCreator;
import com.twitterbot.data.retrieve.SendJsonBodyConnectionModifier;
import com.twitterbot.data.retrieve.UrlCountRetriever;
import com.twitterbot.data.retrieve.UsernameBody;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialMedia {
    Twitter("Twitter followers", new UrlCountRetriever(
            new PlainUrlCreator("https://seekmetrics-server.herokuapp.com/api/v2/live-counter-twitter"),
            new ListConnectionModifier(new JsonContentConnectionModifier(), new POSTConnectionModifier(), new DoOutputConnectionModifier(), new SendJsonBodyConnectionModifier<>(UsernameBody::new)),
            new JsonCountExtractor<>(FollowersBody.class, FollowersBody::getFollowers))),
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
