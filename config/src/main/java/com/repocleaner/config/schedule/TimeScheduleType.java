package com.repocleaner.config.schedule;

import com.repocleaner.model.config.ScheduleType;

public class TimeScheduleType implements ScheduleType {
    public String getType() {
        return "time";
    }
}
