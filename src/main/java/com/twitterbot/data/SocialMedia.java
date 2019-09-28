package com.twitterbot.data;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialMedia {
    Twitter("Twitter followers") {
        @Override
        public int getLatestReal(String account) {
            System.out.println("Getting twitter followers for " + account);
            String url = "https://twitter.com/" + account;
            try {
                Document doc = Jsoup.connect(url).get();
                Elements list = doc.select("li[class=ProfileNav-item ProfileNav-item--followers]");
                Elements value = list.select("span[class=ProfileNav-value]");
                String count = value.attr("data-count");
                return toInt(count);
            } catch (IOException e) {
                return -1;
            }
        }
    },
    Facebook("Facebook likes") {
        @Override
        public int getLatestReal(String account) {
            System.out.println("Getting facebook likes for " + account);
            String url = "https://facebook.com/" + account;
            try {
                Document doc = Jsoup.connect(url).get();
                Elements likes = doc.select("span[id=PagesLikesCountDOMID]");
                Elements value = likes.select("span[class=_52id _50f5 _50f7]");
                String text = value.text();
                String count = text.substring(0, text.length() - 6);
                return toInt(count);
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
    },
    Instagram("Instagram followers") {
        private final Pattern COUNT_PATTERN = Pattern.compile("\"edge_followed_by\" *: *\\{ *\"count\" *: *(\\d+)}");

        @Override
        public int getLatestReal(String account) {
            System.out.println("Getting instagram followers for " + account);
            String url = "https://instagram.com/" + account;
            try {
                String html = Jsoup.connect(url).get().toString();
                Matcher matcher = COUNT_PATTERN.matcher(html);
                if (matcher.find()) {
                    String count = matcher.group(1);
                    return toInt(count);
                } else {
                    System.out.println("Couldn't find count pattern in instagram page");
                    return -1;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
    },
    YouTube("YouTube views") {
        @Override
        public int getLatestReal(String account) {
            System.out.println("Getting youtube views for " + account);
            String url = "https://youtube.com/" + account + "/about";
            try {
                Document doc = Jsoup.connect(url).get();
                Elements divs = doc.select("div[class=about-metadata-container]");
                Elements spans = divs.select("span[class=about-stat]");
                Elements stats = spans.select("b");
                String text = stats.text();
                return toInt(text);
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
    };

    @Getter
    private final String accountName;

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

    public final int getLatest(String account) {
        if (account == null || account.isEmpty()) {
            return -1;
        } else {
            return getLatestReal(account);
        }
    }

    public abstract int getLatestReal(String account);
}
