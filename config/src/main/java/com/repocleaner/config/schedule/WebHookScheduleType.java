package com.repocleaner.config.schedule;

import com.repocleaner.model.config.ScheduleType;

public class WebHookScheduleType implements ScheduleType {
    public String getType() {
        return "webhook";
    }
}
