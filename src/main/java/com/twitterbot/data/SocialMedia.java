package com.twitterbot.data;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialMedia {
    Twitter("Twitter followers", "title *= *\"([0-9,]+) *Followers *\"", "Something went wrong") {
        @Override
        public String createAccountUrl(String account) {
            return "https://twitter.com/" + account;
        }
    },
    Facebook("Facebook likes", "([0-9,]+) *< *span *class *= *\"_50f8 _50f4 _5kx5\" *> *likes", null) {
        @Override
        public String createAccountUrl(String account) {
            return "https://facebook.com/" + account;
        }
    },
    Instagram("Instagram followers", "\"edge_followed_by\" *: *\\{ *\"count\" *: *(\\d+) *}", null) {
        @Override
        public String createAccountUrl(String account) {
            return "https://instagram.com/" + account;
        }
    },
    YouTube("YouTube views", "<span *class *= *\"about-stat\" *>.*< *b *> *([0-9,]+) *< */b *> *views *< */span *>", null) {
        @Override
        public String createAccountUrl(String account) {
            return "https://youtube.com/" + account + "/about";
        }
    };
    private static final int RETRIES = 3;
    @Getter
    private final String accountDescription;
    private final Pattern countPattern;
    private final Pattern errorPattern;

    SocialMedia(String accountDescription, String countRegex, String errorRegex) {
        this.accountDescription = accountDescription;
        this.countPattern = Pattern.compile(countRegex);
        this.errorPattern = (errorRegex == null)
                ? null
                : Pattern.compile(errorRegex);
    }

    public abstract String createAccountUrl(String account);

    public int getLatest(String account) {
        if (account == null || account.isEmpty()) {
            return -1;
        }
        System.out.println("Getting " + getAccountDescription() + " for " + account);
        String url = createAccountUrl(account);
        for (int i = 0; i < RETRIES; i++) {
            String content = getContent(url);
            if (content == null) {
                if (i != RETRIES - 1) {
                    sleep();
                }
            } else {
                Matcher matcher = countPattern.matcher(content);
                if (matcher.find()) {
                    String count = matcher.group(1);
                    return toInt(count);
                } else {
                    if (hasKnownError(content)) {
                        System.out.println("Error detected " + name() + " pattern for account " + account);
                    } else {
                        System.out.println("Unknown error detected " + name() + " pattern for account " + account + " for content");
                        System.out.println(content);
                    }
                }
            }
        }
        return -1;
    }

    private String getContent(String url) {
        for (int i = 0; i < RETRIES; i++) {
            try {
                Document doc = Jsoup.connect(url).get();
                return doc.body().html();
            } catch (IOException e) {
                e.printStackTrace();
                if (i != RETRIES - 1) {
                    sleep();
                }
            }
        }
        return null;
    }

    private boolean hasKnownError(String content) {
        if (errorPattern != null) {
            Matcher errorMatcher = errorPattern.matcher(content);
            return errorMatcher.find();
        }
        return false;
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int toInt(String count) {
        String noCommas = count.replace(",", "");
        try {
            return noCommas.isEmpty()
                    ? 0
                    : Integer.parseInt(noCommas);
        } catch (NumberFormatException e) {
            System.out.println(name() + " couldn't parse: " + count + " to int");
            return -1;
        }
    }
}
