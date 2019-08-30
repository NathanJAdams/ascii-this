package com.twitterbot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class StatsRange {
    private final double min;
    private final double max;
}
