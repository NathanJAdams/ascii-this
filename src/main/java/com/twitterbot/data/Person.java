package com.twitterbot.data;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Person {
    private final String name;
    private final String affiliation;
    private final Map<SocialMedia, String> accounts;
}
