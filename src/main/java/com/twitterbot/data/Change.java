package com.twitterbot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Change {
    private final double ratioChange;
    private final double rawChange;
}
