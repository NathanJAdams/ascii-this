package com.repocleaner.coreclean.transform;

public enum SuccessStrategy {
    Restart {
        @Override
        public int calculateNextIndex(int currentIndex, int size) {
            return 0;
        }
    },
    Repeat {
        @Override
        public int calculateNextIndex(int currentIndex, int size) {
            return currentIndex;
        }
    },
    Continue {
        @Override
        public int calculateNextIndex(int currentIndex, int size) {
            return currentIndex + 1;
        }
    };

    public abstract int calculateNextIndex(int currentIndex, int size);
}
