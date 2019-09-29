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
    Twitter("Twitter followers", "title *= *\"([0-9,]+) *Followers *\"") {
        @Override
        public String createAccountUrl(String account) {
            return "https://twitter.com/" + account;
        }
    },
    Facebook("Facebook likes", "([0-9,]+) *< *span *class *= *\"_50f8 _50f4 _5kx5\" *> *likes") {
        @Override
        public String createAccountUrl(String account) {
            return "https://facebook.com/" + account;
        }
    },
    Instagram("Instagram followers", "\"edge_followed_by\" *: *\\{ *\"count\" *: *(\\d+) *}") {
        @Override
        public String createAccountUrl(String account) {
            return "https://instagram.com/" + account;
        }
    },
    YouTube("YouTube views", "<span *class *= *\"about-stat\" *>.*< *b *> *([0-9,]+) *< */b *> *views *< */span *>") {
        @Override
        public String createAccountUrl(String account) {
            return "https://youtube.com/" + account + "/about";
        }
    };

    @Getter
    private final String accountDescription;
    private final Pattern countPattern;

    SocialMedia(String accountDescription, String countRegex) {
        this.accountDescription = accountDescription;
        this.countPattern = Pattern.compile(countRegex);
    }

    public abstract String createAccountUrl(String account);

    public int getLatest(String account) {
        if (account == null || account.isEmpty()) {
            return -1;
        }
        System.out.println("Getting " + getAccountDescription() + " for " + account);
        String url = createAccountUrl(account);
        try {
            Document doc = Jsoup.connect(url).get();
            String html = doc.body().html();
            Matcher matcher = countPattern.matcher(html);
            if (matcher.find()) {
                String count = matcher.group(1);
                return toInt(count);
            } else {
                System.out.println("Couldn't find count " + name() + " pattern for account " + account);
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
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
