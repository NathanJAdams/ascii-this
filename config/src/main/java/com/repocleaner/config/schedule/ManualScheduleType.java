package com.repocleaner.config.schedule;

import com.repocleaner.model.config.ScheduleType;

public class ManualScheduleType implements ScheduleType {
    public String getType() {
        return "manual";
    }
}
