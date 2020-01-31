package com.twitterbot.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialMedia {
    Twitter("Twitter followers", Collections.singletonList("title *= *\"([0-9,]+) *Followers *\""), "Something went wrong") {
        @Override
        public String createAccountUrl(String account) {
            return "https://twitter.com/" + account;
        }
    },
    Facebook("Facebook likes", Collections.singletonList("([0-9,]+) *< *span *class *= *\"_50f8 _50f4 _5kx5\" *> *likes"), null) {
        @Override
        public String createAccountUrl(String account) {
            return "https://facebook.com/" + account;
        }
    },
    Instagram("Instagram followers", Collections.singletonList("\"edge_followed_by\" *: *\\{ *\"count\" *: *(\\d+) *}"), null) {
        @Override
        public String createAccountUrl(String account) {
            return "https://instagram.com/" + account;
        }
    },
    YouTube("YouTube views", Arrays.asList("<span *class *= *\"about-stat\" *>.*< *b *> *([0-9,]+) *< */b *> *views *< */span *>", "\"viewCountText\"[\\n\\r\\s]*:[\\n\\r\\s]*\\{[\\n\\r\\s]*\"runs\"[\\n\\r\\s]*:[\\n\\r\\s]*\\[[\\n\\r\\s]*\\{[\\n\\r\\s]*\"text\"[\\n\\r\\s]*:[\\n\\r\\s]*\"([0-9,]+)\"[\\n\\r\\s]*,"), null) {
        @Override
        public String createAccountUrl(String account) {
            return "https://youtube.com/" + account + "/about";
        }
    };

    private static final int RETRIES = 3;

    @Getter
    private final String accountDescription;
    private final List<Pattern> countPatterns;
    private final Pattern errorPattern;

    SocialMedia(String accountDescription, List<String> countRegexes, String errorRegex) {
        this.accountDescription = accountDescription;
        this.countPatterns = countRegexes.stream().map(Pattern::compile).collect(Collectors.toList());
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
                int count = getCountFromContent(content);
                if (count != -1) {
                    return count;
                }
            }
        }
        return -1;
    }

    private int getCountFromContent(String content) {
        for (Pattern countPattern : countPatterns) {
            Matcher matcher = countPattern.matcher(content);
            if (matcher.find()) {
                String count = matcher.group(1);
                return toInt(count);
            }
        }
        if (hasKnownError(content)) {
            System.out.println("Error detected " + name() + " pattern");
        } else {
            System.out.println("Unknown error detected " + name() + " pattern for content");
            System.out.println(content);
        }
        System.out.println(content);
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
