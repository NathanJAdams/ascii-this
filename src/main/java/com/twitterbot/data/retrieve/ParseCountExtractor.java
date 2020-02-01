package com.twitterbot.data.retrieve;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseCountExtractor implements CountExtractor {
    private final Pattern pattern;

    public ParseCountExtractor(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public int extract(String content) {
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String match = matcher.group(1);
            return toInt(match);
        }
        System.out.println("Couldn't find match for pattern: " + pattern);
        System.out.println(content);
        return -1;
    }

    public int toInt(String count) {
        String noCommas = count.replace(",", "");
        try {
            return noCommas.isEmpty()
                    ? 0
                    : Integer.parseInt(noCommas);
        } catch (NumberFormatException e) {
            System.out.println("Couldn't parse: " + count + " to int");
            return -1;
        }
    }
}
