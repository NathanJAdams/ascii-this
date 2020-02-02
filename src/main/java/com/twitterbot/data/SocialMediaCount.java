package com.twitterbot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class SocialMediaCount {
    private final Map<SocialMedia, Integer> socialMediaCounts;
}
