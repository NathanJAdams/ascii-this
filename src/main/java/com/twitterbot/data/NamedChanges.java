package com.twitterbot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class NamedChanges {
    private final Map<String, SocialMediaChanges> namedChanges;
}
