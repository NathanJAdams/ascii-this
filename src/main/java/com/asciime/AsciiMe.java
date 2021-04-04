package com.asciime;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class AsciiMe implements RequestHandler<S3Event, Void> {
    public Void handleRequest(S3Event event, Context context) {
        System.out.println("Hello!");
        return null;
    }

    private void tweet() {
        long[] mediaIDs = {};
        String text = "hello";
        System.out.println(text);
        TwitterAPI.tweet(text, mediaIDs);
    }
}
