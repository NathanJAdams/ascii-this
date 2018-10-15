package com.repocleaner.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

@AllArgsConstructor
@ToString
public enum Timing {
    Hourly(ChronoUnit.HOURS),
    Daily(ChronoUnit.DAYS),
    Weekly(ChronoUnit.WEEKS),
    Monthly(ChronoUnit.MONTHS);
    private final TemporalUnit temporalUnit;

    public LocalDateTime getNextCleanTime(LocalDateTime previous) {
        return previous.plus(1, temporalUnit);
    }
}
