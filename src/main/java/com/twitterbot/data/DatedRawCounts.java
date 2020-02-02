package com.twitterbot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DatedRawCounts {
    private String tag;
    private long epochDay;
    private Map<String, SocialMediaCount> accountSocialMediaCounts;
}
