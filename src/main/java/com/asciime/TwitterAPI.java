package com.asciime;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

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
