package com.twitterbot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class Person {
    private final String name;
    private final String affiliation;
    private final Map<SocialMedia, String> accounts;
}
