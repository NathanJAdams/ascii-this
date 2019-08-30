package com.twitterbot.data;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DatedRawCounts {
    private String tag;
    private long epochDay;
    private Map<String, SocialMediaCount> accountSocialMediaCounts;
}
