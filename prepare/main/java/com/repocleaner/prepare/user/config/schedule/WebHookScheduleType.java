package com.repocleaner.clean.user.config.schedule;

public class WebHookScheduleType implements ScheduleType {
    public String getType() {
        return "webhook";
    }
}
