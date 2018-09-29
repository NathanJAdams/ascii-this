package com.repocleaner.util;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixCommander {
    public static <T> T run(String group, ExternalCommand<T> externalCommand) {
        return run(group, externalCommand, null);
    }

    public static <T> T run(String group, ExternalCommand<T> externalCommand, T defaultValue) {
        return new Command<T>(group, externalCommand, defaultValue).execute();
    }

    @FunctionalInterface
    public interface ExternalCommand<T> {
        T run() throws Exception;
    }

    private static class Command<T> extends HystrixCommand<T> {
        private final ExternalCommand<T> externalCommand;
        private final T defaultValue;

        public Command(String group, ExternalCommand<T> externalCommand, T defaultValue) {
            super(HystrixCommandGroupKey.Factory.asKey(group));
            this.externalCommand = externalCommand;
            this.defaultValue = defaultValue;
        }

        @Override
        protected T run() throws Exception {
            return externalCommand.run();
        }

        @Override
        protected T getFallback() {
            return defaultValue;
        }
    }
}
