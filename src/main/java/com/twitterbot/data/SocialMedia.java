package com.twitterbot.data;

import java.io.IOException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialMedia {
    Twitter("Twitter followers", "user", 150) {
        @Override
        public int getLatest(String account) {
            System.out.println("Getting twitter followers for " + account);
            String url = "https://twitter.com/" + account;
            try {
                Document doc = Jsoup.connect(url).get();
                Elements list = doc.select("li[class=ProfileNav-item ProfileNav-item--followers]");
                Elements value = list.select("span[class=ProfileNav-value]");
                String count = value.attr("data-count");
                return count.isEmpty()
                        ? 0
                        : Integer.parseInt(count);
            } catch (IOException | NumberFormatException e) {
                return -1;
            }
        }
    },
    Facebook("Facebook likes", "page", 120) {
        @Override
        public int getLatest(String account) {
            System.out.println("Getting facebook likes for " + account);
            String url = "https://facebook.com/" + account;
            try {
                Document doc = Jsoup.connect(url).get();
                Elements likes = doc.select("span[id=PagesLikesCountDOMID]");
                Elements value = likes.select("span[class=_52id _50f5 _50f7]");
                String text = value.text();
                String count = text.substring(0, text.length() - 6).replace(",", "");
                return count.isEmpty()
                        ? 0
                        : Integer.parseInt(count);
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
                return -1;
            }
        }
    };

    @Getter
    private final String accountName;
    private final String type;
    private final int pixelsWide;

    public abstract int getLatest(String account);

    public int[] getCounts(String account) {
        int[] counts = new int[10];
        counts[0] = (int) (Math.random() * 100);
        for (int i = 1; i < counts.length; i++) {
            counts[i] = counts[i - 1] + (int) (Math.random() * 100);
        }
        return counts;
//        String url = "https://socialblade.com/" + name().toLowerCase() + "/" + type + "/" + account;
//        try {
//            Document doc = Jsoup.connect(url).get();
//            Elements list = doc.select("div[style=\"width: " + pixelsWide + "px; float: left;\"]");
//            return list.eachText()
//                    .stream()
//                    .mapToInt(this::toInt)
//                    .toArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    private int toInt(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        value = value.replace(",", "").replace(" LIVE", "");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
