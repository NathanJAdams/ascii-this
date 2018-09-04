package com.repocleaner.model.config;

import com.repocleaner.util.LocalDateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Config {
    private boolean isPaused;
    private int maxTokensPerClean;
    private List<SplitType> splitTypes;
    private TransformationPriority priority;
    private List<String> languages;
    private String lastCleanedTimeHex;
    private String nextCleanTimeHex;

    public LocalDateTime getLastCleanedTime() {
        return LocalDateTimeUtil.toLocalDateTime(lastCleanedTimeHex);
    }

    public void setLastCleanedTime(LocalDateTime lastCleanedTime) {
        this.lastCleanedTimeHex = LocalDateTimeUtil.toHex(lastCleanedTime);
    }

    public LocalDateTime getNextCleanTime() {
        return LocalDateTimeUtil.toLocalDateTime(nextCleanTimeHex);
    }

    public void setNextCleanTime(LocalDateTime nextCleanTime) {
        this.nextCleanTimeHex = LocalDateTimeUtil.toHex(nextCleanTime);
    }
}
