package com.twitterbot.data;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SocialMediaChanges {
    private final Map<SocialMedia, Change> changes;
}