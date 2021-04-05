package com.asciime;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class TwitterAPI {
    private static final long USER_ID = 1378896391196512259L;

    private static final Twitter TWITTER = new TwitterFactory(
            new ConfigurationBuilder()
                    .setOAuthConsumerKey(System.getenv("CONSUMER_KEY"))
                    .setOAuthConsumerSecret(System.getenv("CONSUMER_SECRET_KEY"))
                    .setOAuthAccessToken(System.getenv("ACCESS_TOKEN"))
                    .setOAuthAccessTokenSecret(System.getenv("ACCESS_TOKEN_SECRET"))
                    .build())
            .getInstance();

    public static Status status(long statusID) {
        AtomicReference<ResponseList<Status>> status = new AtomicReference<>();
        doTwitterAction(t -> status.set(t.lookup(statusID)));
        ResponseList<Status> list = status.get();
        return (list == null) || (list.size() != 1)
                ? null
                : list.get(0);
    }

    public static Status tweet(Status replyTo, String text) {
        return tweet(replyTo, text, null);
    }

    public static Status tweet(Status replyTo, long[] mediaIDs) {
        return tweet(replyTo, null, mediaIDs);
    }

    public static Status tweet(Status replyTo, String text, long[] mediaIDs) {
        StringBuilder sb = new StringBuilder();
        sb.append('@');
        sb.append(replyTo.getUser().getScreenName());
        if (text != null) {
            sb.append('\n');
            sb.append(text);
        }
        StatusUpdate statusUpdate = new StatusUpdate(sb.toString());
        statusUpdate.setMediaIds(mediaIDs);
        statusUpdate.setInReplyToStatusId(replyTo.getId());
        AtomicReference<Status> status = new AtomicReference<>();
        doTwitterAction(t -> status.set(t.updateStatus(statusUpdate)));
        return status.get();
    }

    public static long uploadFile(File file) {
        AtomicLong mediaID = new AtomicLong();
        doTwitterAction(t -> mediaID.set(t.uploadMedia(file).getMediaId()));
        return mediaID.get();
    }

    public static List<Status> getMentionsSince(long sinceId) {
        Paging paging = new Paging();
        paging.setCount(100);
        paging.setSinceId(sinceId);
        List<Status> statuses = new ArrayList<>();
        try {
            for (ResponseList<Status> responseList = TWITTER.getMentionsTimeline(paging);
                 !responseList.isEmpty();
                 responseList = TWITTER.getMentionsTimeline(paging)) {
                statuses.addAll(responseList);
                Status last = responseList.get(responseList.size() - 1);
                paging.setMaxId(last.getId() - 1);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return statuses;
    }

    public static void sendDMToSelf(String text) {
        doTwitterAction(t -> t.sendDirectMessage(USER_ID, text));
    }

    public static DirectMessage getLatestDM() {
        AtomicReference<DirectMessageList> response = new AtomicReference<>();
        doTwitterAction(t -> response.set(t.getDirectMessages(1)));
        DirectMessageList list = response.get();
        return (list == null) || list.isEmpty()
                ? null
                : list.get(0);
    }

    private static void doTwitterAction(TwitterAction action) {
        try {
            action.action(TWITTER);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface TwitterAction {
        void action(Twitter twitter) throws TwitterException;
    }
}
