package com.repocleaner.config.schedule;

public class WebHookScheduleType implements ScheduleType {
    public String getType() {
        return "webhook";
    }
}
