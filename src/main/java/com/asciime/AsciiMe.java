package com.asciime;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import twitter4j.DirectMessage;
import twitter4j.MediaEntity;
import twitter4j.Status;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AsciiMe implements RequestHandler<S3Event, Void> {
    public static void main(String[] args) {
        new AsciiMe().handleRequest(null, null);
    }

    public Void handleRequest(S3Event event, Context context) {
        DirectMessage latestDM = TwitterAPI.getLatestDM();
        long sinceId = parseTextAsLong(latestDM, 1);
        System.out.println("Handling mentions since id: " + sinceId);
        List<Status> mentions = TwitterAPI.getMentionsSince(sinceId);
        mentions.forEach(this::handleMention);
        mentions.stream()
                .map(Status::getId)
                .mapToLong(id -> id)
                .max()
                .ifPresent(latestMention -> TwitterAPI.sendDMToSelf(String.valueOf(latestMention)));
        return null;
    }

    private void handleMention(Status status) {
        long requestTweetID = status.getId();
        System.out.println("requestTweetID: " + requestTweetID);
        Status mediaTweet = getMediaTweet(status);
        Status replyStatus = null;
        if (mediaTweet == null) {
            replyStatus = TwitterAPI.tweet(status, "Sorry I couldn't find any photos to convert");
        } else if (TwitterAPI.USER_ID != mediaTweet.getUser().getId()) {
            replyStatus = reply(status, mediaTweet);
        }
        if (replyStatus != null) {
            System.out.println("replyTweet: " + replyStatus.getId());
        }
    }

    private Status getMediaTweet(Status status) {
        if (canConvert(status)) {
            return status;
        }
        long inReplyToStatusId = status.getInReplyToStatusId();
        Status inReplyToStatus = TwitterAPI.status(inReplyToStatusId);
        if (canConvert(inReplyToStatus)) {
            return inReplyToStatus;
        }
        return null;
    }

    private Status reply(Status requestStatus, Status mediaStatus) {
        System.out.println("requestStatus: " + requestStatus.getId() + ", mediaStatus: " + mediaStatus.getId());
        File[] files = Arrays.stream(mediaStatus.getMediaEntities())
                .map(MediaEntity::getMediaURL)
                .map(ImageDownloader::toImage)
                .filter((Objects::nonNull))
                .map(AsciiConverter::convert)
                .map(ImageSaver::toFile)
                .filter((Objects::nonNull))
                .toArray(File[]::new);
        long[] mediaIDs = Arrays.stream(files)
                .map(TwitterAPI::uploadFile)
                .mapToLong(id -> id)
                .toArray();
        Status replyStatus = TwitterAPI.tweet(requestStatus, mediaIDs);
        Arrays.stream(files).forEach(File::delete);
        return replyStatus;
    }

    private boolean canConvert(Status status) {
        return (status != null) && Arrays.stream(status.getMediaEntities())
                .anyMatch(me -> "photo".equals(me.getType()));
    }

    private long parseTextAsLong(DirectMessage dm, long defaultValue) {
        if (dm == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(dm.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
