package com.twitterbot.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Theme {
    Percentage("Viral", "Percentage", Change::getRatioChange, getPercentFormat()),
    Raw("Popular", "Average", Change::getRawChange, NumberFormat.getIntegerInstance());

    private final String label;
    private final String metric;
    private final ToDoubleFunction<Change> func;
    private final NumberFormat format;

    private static NumberFormat getPercentFormat() {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(2);
        percentFormat.setMaximumFractionDigits(2);
        return percentFormat;
    }

    public StatsRange getStatsRange(SocialMedia socialMedia, List<Map.Entry<Person, SocialMediaChanges>> sorted) {
        double min = Math.min(0, getExtreme(socialMedia, sorted, true));
        double max = Math.max(0, getExtreme(socialMedia, sorted, false));
        return new StatsRange(min, max);
    }

    private double getExtreme(SocialMedia socialMedia, List<Map.Entry<Person, SocialMediaChanges>> sorted, boolean isMin) {
        DoubleStream stream = sorted
                .stream()
                .map(socialMediaChanges -> socialMediaChanges.getValue().getChanges().get(socialMedia))
                .filter(Objects::nonNull)
                .mapToDouble(func);
        OptionalDouble optionalDouble = isMin
                ? stream.min()
                : stream.max();
        return optionalDouble.orElse(0);
    }
}
