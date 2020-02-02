package com.twitterbot;

import twitter4j.DirectMessage;
import twitter4j.DirectMessageList;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class TwitterAPI {
    public static void tweet(String text, long[] mediaIDs) {
        StatusUpdate statusUpdate = new StatusUpdate(text);
        statusUpdate.setMediaIds(mediaIDs);
        doTwitterAction(t -> t.updateStatus(statusUpdate));
    }

    public static long uploadFile(File file) {
        AtomicLong mediaID = new AtomicLong();
        doTwitterAction(t -> mediaID.set(t.uploadMedia(file).getMediaId()));
        return mediaID.get();
    }

    public static void dm(String name, String text) {
        doTwitterAction(t -> t.sendDirectMessage(name, text));
    }

    public static List<DirectMessage> getDMs(Predicate<DirectMessage> filter) {
        List<DirectMessage> dms = new ArrayList<>();
        AtomicReference<DirectMessageList> response = new AtomicReference<>();
        doTwitterAction(t -> response.set(t.getDirectMessages(50)));
        DirectMessageList list = response.get();
        if (!list.isEmpty()) {
            for (DirectMessage dm : list) {
                if (filter.test(dm)) {
                    dms.add(dm);
                }
            }
            String nextCursor = list.getNextCursor();
            if (nextCursor != null) {
                doTwitterAction(t -> response.set(t.getDirectMessages(50, nextCursor)));
            }
        }
        return dms;
    }

    public static int getFollowerCount(String account) {
        AtomicInteger followerCount = new AtomicInteger(-1);
        doTwitterAction(twitter -> {
            User user = twitter.showUser(account);
            followerCount.set(user.getFollowersCount());
        });
        return followerCount.get();
    }

    private static void doTwitterAction(TwitterAction action) {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            action.action(twitter);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface TwitterAction {
        void action(Twitter twitter) throws TwitterException;
    }
}
